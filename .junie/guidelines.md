# Junie Project Development Guidelines

## 🧩 프로젝트 구분

- `mybatis-lms`: MyBatis 기반의 LMS 시스템
- `jpa-shop`: JPA 기반의 쇼핑몰 또는 도메인 중심 시스템

---

## 📦 공통 Build & Configuration

- Java 버전: 17 이상 (LTS)
- 빌드 도구: Gradle 8 이상
- 환경 변수는 `.env` 또는 `application-*.properties` 사용
- 공통 모듈은 `/common`, 각 도메인별 `/-*`로 분리되어 있음

---

## 📁 1. MyBatis 기반 (project: `mybatis-project`)

### ✅ Repository 설계
- Mapper는 **EntityMapper** 와 **QueryMapper** 로 구분합니다.
- **등록/수정/삭제/단건조회**는 `EntityMapper` 에 작성합니다.
- **화면 전용 쿼리, 다중조인, 복합 조회**는 `QueryMapper` 에 작성합니다.
- SQL은 모두 XML에 작성하며, 동적 쿼리는 `where`, `if`, `choose`를 사용하여 가독성을 확보합니다.

### ✅ Entity 작성 규칙
- `@Getter`만 사용하여 캡슐화하고, setter는 사용하지 않습니다.
- `private 생성자 + static 생성 메서드`를 통해 선언적인 객체 생성을 지향합니다.
- 상태 변경, 유효성 검증 로직은 Entity 내부 메서드로 위임합니다.
- Entity는 테이블과 1:1로 매핑하며, View나 화면용 필드는 DTO로 분리합니다.
- 원시 타입 대신 `Email`, `DeleteYn`, `MemberStatus` 등 **도메인 객체 래핑 클래스**를 적극 사용합니다.

### ✅ DTO 작성 규칙
- Entity와는 완전히 분리된 클래스입니다.
- RequestDTO는 `@Valid`로 검증, ResponseDTO는 민감정보 필터링 필수
- `from()` 또는 `to()` 정적 팩토리 메서드를 통해 DTO <-> Entity 변환

### ✅ 테스트 전략
- `@MybatisTest` 또는 `@SpringBootTest`로 Mapper 및 통합 테스트 작성
- 테스트 Entity는 `static create*()` 메서드 사용

#### 예시 테스트 코드
```java
@SpringBootTest
class MemberMapperTest {
    @Autowired MemberMapper memberMapper;

    @Test
    void 사용자_조회가_정상적으로_동작한다() {
        // given
        Long userId = 1L;

        // when
        Member member = memberMapper.findById(userId);

        // then
        assertThat(member.getName()).isEqualTo("홍길동");
    }
}
```

### ✅ 개발 규칙
- 서비스 레이어는 Entity의 메서드를 호출하여 로직 위임
- 상태 체크, 변경 로직은 Entity 내부에 존재해야 함
- 테스트는 Entity + Mapper 단위로 명확히 구분 작성

---

## 📁 2. JPA 기반 (project: `jpa-project`)

### ✅ Repository 설계
- `JpaRepository` 또는 `Querydsl`을 기반으로 구현
- Entity는 `@Entity`, DTO는 `record` 또는 Projection 기반
- 연관관계는 최대한 명확하게 설정, 필요 시 Fetch Join 또는 BatchSize 적용

### ✅ 테스트 전략
- `@DataJpaTest` 또는 `@SpringBootTest` 사용
- 테스트 격리를 위해 `@Transactional` 기본 적용

#### 예시 테스트 코드
```java
@DataJpaTest
class ProductRepositoryTest {
    @Autowired ProductRepository productRepository;

    @Test
    void 상품_저장이_정상적으로_이루어진다() {
        // given
        Product product = new Product("상품명", 10000);

        // when
        Product saved = productRepository.save(product);

        // then
        assertThat(saved.getId()).isNotNull();
    }
}
```

### ✅ 개발 규칙
- Entity 설계 시 식별자, 생성일자, 수정일자 메타데이터 공통 포함 (`BaseEntity` 상속)
- Repository는 반드시 interface, 구현체는 JPA에 위임
- 서비스 단에서 Lazy 문제 방지 위한 전략 명확히 (Fetch Join, DTO 매핑)

---

## 🛠 기타 Notes

- 공통 Response 객체 (`CommonResponse<T>`)를 컨트롤러 응답 포맷으로 통일
- 서비스 단에는 트랜잭션 명확히 구분: 읽기 `@Transactional(readOnly = true)`
- 테스트 커버리지 80% 이상 유지 목표

---

> 본 문서는 Junie AI의 정확한 응답을 돕기 위해 작성되었으며,
> 개발자 onboarding 및 유지보수에도 활용 가능합니다.