
## Fitur yg di implement dari soal
  - [Spring IoC](#1-spring-ioc)
  - [Java Stream](#2-java-stream)
  - [Advanced Native SQL Query](#3-advanced-native-sql-query)
  - [Containerization & Microservices](#4-containerization--microservices)
  - [Redis dan Caching Strategy](#5-redis-dan-caching-strategy)

## Fitur Utama

- Manajemen data anggota koperasi.
- Manajemen data simpanan anggota.
- Manajemen data pinjaman anggota.
- Update status pinjaman.
- Dashboard laporan keuangan anggota.
- Dashboard tingkat persetujuan pinjaman.
- Dashboard anggota paling aktif.
- Dashboard anggota berisiko tinggi.
- Caching dashboard menggunakan Redis.
- Infrastruktur service menggunakan Docker Compose.

## Tech Stack

| Komponen | Teknologi |
|---|---|
| Bahasa | Java 21 |
| Framework | Spring Boot |
| Web API | Spring Web MVC / REST Controller |
| ORM | Spring Data JPA / Hibernate |
| Database utama | PostgreSQL |
| Cache | Redis |
| Container | Docker Compose |
| Build Tool | Maven |
| Utility | Lombok |

## Arsitektur Singkat

Project menggunakan pola berlapis sederhana:

```text
Client / Postman
       ↓
Controller Layer
       ↓
Service Layer
       ↓
Repository Layer
       ↓
PostgreSQL / Redis
```

Penjelasan layer:

| Layer | Fungsi |
|---|---|
| Controller | Menerima request HTTP dan mengembalikan response API. |
| Service | Menjalankan business logic, mapping entity ke DTO, dan caching. |
| Repository | Mengakses database menggunakan Spring Data JPA dan native query. |
| Entity | Merepresentasikan tabel database. |
| DTO | Menjadi format request dan response API. |
| Config | Menyimpan konfigurasi tambahan seperti Redis cache. |

## Struktur Project

```text
koperasi/
├── Dockerfile
├── .dockerignore
├── docker-compose.yml
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/com/example/koperasi/
    │   │   ├── Main.java
    │   │   ├── config/
    │   │   │   └── RedisConfig.java
    │   │   ├── controller/
    │   │   │   ├── DashboardController.java
    │   │   │   ├── LoanController.java
    │   │   │   ├── MemberController.java
    │   │   │   └── SavingController.java
    │   │   ├── model/
    │   │   │   ├── dto/
    │   │   │   └── entity/
    │   │   ├── repositories/
    │   │   └── services/
    │   └── resources/
    │       └── application.yaml
    └── test/
```

## Konfigurasi

Konfigurasi utama berada di:

```text
src/main/resources/application.yaml
```

Konfigurasi database:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/koperasi_db
    username: postgres
    password: postgres
    driver-class-name: org.postgresql.Driver
```

Konfigurasi Redis:

```yaml
spring:
  cache:
    type: redis

  data:
    redis:
      host: localhost
      port: 6379
```

Port aplikasi:

```yaml
server:
  port: 8085
```

## Cara Menjalankan Project

### 1. Jalankan aplikasi lengkap dengan Docker Compose

Perintah ini akan menjalankan aplikasi Spring Boot, PostgreSQL, dan Redis dalam container terpisah.

```bash
docker compose up -d --build
```

Service yang tersedia di `docker-compose.yml`:

| Service | Port | Fungsi |
|---|---:|---|
| PostgreSQL | 5432 | Database utama aplikasi. |
| Redis | 6379 | Penyimpanan cache dashboard. |
| MongoDB | 27017 | Service pendukung yang tersedia di container. |
| Elasticsearch | 9200 | Service pendukung yang tersedia di container. |

### 2. Jalankan aplikasi Spring Boot

Menggunakan Maven wrapper:

```bash
./mvnw spring-boot:run
```

Untuk Windows:

```bash
mvnw.cmd spring-boot:run
```

Atau menggunakan Maven lokal:

```bash
mvn spring-boot:run
```

Aplikasi berjalan di:

```text
http://localhost:8085
```

### 3. Stop container

```bash
docker compose down
```

Jika ingin menghapus volume data juga:

```bash
docker compose down -v
```

## Endpoint API

### Member API

Base path:

```text
/v1/api/members
```

| Method | Endpoint | Fungsi |
|---|---|---|
| POST | `/v1/api/members` | Membuat anggota baru. |
| GET | `/v1/api/members` | Mengambil semua data anggota. |
| GET | `/v1/api/members/{id}` | Mengambil anggota berdasarkan ID. |
| GET | `/v1/api/members/email/{email}` | Mengambil anggota berdasarkan email. |
| PUT | `/v1/api/members/{id}` | Mengubah data anggota. |
| DELETE | `/v1/api/members/{id}` | Menghapus data anggota. |

### Saving API

Base path:

```text
/v1/api/saving
```

| Method | Endpoint | Fungsi |
|---|---|---|
| POST | `/v1/api/saving` | Membuat data simpanan. |
| GET | `/v1/api/saving` | Mengambil semua data simpanan. |
| GET | `/v1/api/saving/member/{memberId}` | Mengambil simpanan berdasarkan anggota. |
| GET | `/v1/api/saving/member/{memberId}/total` | Mengambil total simpanan anggota. |

### Loan API

Base path:

```text
/v1/api/loans
```

| Method | Endpoint | Fungsi |
|---|---|---|
| POST | `/v1/api/loans` | Membuat pengajuan pinjaman. |
| GET | `/v1/api/loans` | Mengambil semua data pinjaman. |
| GET | `/v1/api/loans/member/{memberId}` | Mengambil pinjaman berdasarkan anggota. |
| PUT | `/v1/api/loans/{loanId}/status?status=APPROVED` | Mengubah status pinjaman. |

### Dashboard API

Base path:

```text
/v1/api/dashboard
```

| Method | Endpoint | Fungsi |
|---|---|---|
| GET | `/v1/api/dashboard/financial` | Laporan total simpanan, pinjaman, dan saldo bersih anggota. |
| GET | `/v1/api/dashboard/loan-approval` | Persentase persetujuan pinjaman anggota. |
| GET | `/v1/api/dashboard/active-members` | Daftar anggota paling aktif berdasarkan transaksi. |
| GET | `/v1/api/dashboard/risk-members` | Daftar anggota berisiko berdasarkan selisih pinjaman dan simpanan. |

## Contoh Request Body

### Create Member

```http
POST /v1/api/members
Content-Type: application/json
```

```json
{
  "name": "Budi Santoso",
  "email": "budi@example.com",
  "phone": "081234567890"
}
```

### Create Saving

```http
POST /v1/api/saving
Content-Type: application/json
```

```json
{
  "memberId": "isi-dengan-uuid-member",
  "amount": 500000,
  "type": "WAJIB"
}
```

### Create Loan

```http
POST /v1/api/loans
Content-Type: application/json
```

```json
{
  "memberId": "isi-dengan-uuid-member",
  "amount": 3000000,
  "durationMonth": 12,
  "interestRate": 1.5
}
```

### Update Loan Status

```http
PUT /v1/api/loans/{loanId}/status?status=APPROVED
```

Status dapat disesuaikan, misalnya:

```text
PENDING
APPROVED
REJECTED
```

## Penjelasan Implementasi Konsep

### 1. Spring IoC

Spring IoC atau Inversion of Control digunakan agar pembuatan dan pengelolaan object tidak dilakukan secara manual oleh programmer, tetapi dikelola oleh Spring Container.

Contoh implementasi dalam project:

```java
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
}
```

Pada contoh tersebut, `MemberController` membutuhkan `MemberService`. Object `MemberService` tidak dibuat menggunakan `new MemberServiceImpl()`, tetapi otomatis diinjeksi oleh Spring melalui constructor yang dibuat oleh Lombok `@RequiredArgsConstructor`.

Contoh lain:

```java
@RequiredArgsConstructor
@Service
public class SavingServiceImpl implements SavingService {

    private final SavingRepository savingRepository;
    private final MemberRepository memberRepository;
}
```

Penjelasan:

- `@Service` menandai class sebagai komponen service layer.
- `@Repository` menandai interface repository sebagai komponen akses database.
- `@RestController` menandai class sebagai komponen controller REST API.
- `@Configuration` dan `@Bean` digunakan untuk mendefinisikan bean manual, seperti `RedisCacheManager`.

Dengan IoC, dependency antar layer menjadi lebih rapi, mudah diuji, dan tidak tightly coupled.

### 2. Java Stream

Java Stream digunakan untuk memproses collection secara deklaratif, terutama saat melakukan mapping dari entity atau hasil query menjadi DTO response.

Contoh pada `MemberServiceImpl`:

```java
@Override
public List<MemberResponse> getAll() {
    return memberRepository.findAll()
            .stream()
            .map(this::mapToResponse)
            .toList();
}
```

Alur proses:

```text
List<Member> dari database
        ↓ stream()
Mapping setiap Member ke MemberResponse
        ↓ toList()
List<MemberResponse>
```

Contoh pada `DashboardServiceImpl`:

```java
return repository.getMemberFinancialReport()
        .stream()
        .map(o -> MemberFinancialResponse.builder()
                .memberId(UUID.fromString(o[0].toString()))
                .memberName(o[1].toString())
                .totalSaving((BigDecimal) o[2])
                .totalLoan((BigDecimal) o[3])
                .netBalance((BigDecimal) o[4])
                .build())
        .toList();
```

Stream pada bagian tersebut digunakan untuk mengubah hasil native query berbentuk `List<Object[]>` menjadi DTO yang lebih aman dan mudah dibaca oleh client API.

### 3. Advanced Native SQL Query

Project ini menggunakan native SQL query melalui anotasi `@Query(nativeQuery = true)` pada repository. Native SQL digunakan karena beberapa kebutuhan dashboard membutuhkan agregasi, join, conditional calculation, dan perhitungan langsung di database.

Contoh financial report pada `DashboardRepository`:

```java
@Query(value = """
    SELECT 
        m.id,
        m.name,
        COALESCE(SUM(s.amount),0) AS total_saving,
        COALESCE(SUM(l.amount),0) AS total_loan,
        (COALESCE(SUM(s.amount),0) - COALESCE(SUM(l.amount),0)) AS net_balance
    FROM members m
    LEFT JOIN savings s ON m.id = s.member_id
    LEFT JOIN loans l ON m.id = l.member_id
    GROUP BY m.id, m.name
    ORDER BY net_balance DESC
""", nativeQuery = true)
List<Object[]> getMemberFinancialReport();
```

Fungsi query:

- Menggabungkan tabel `members`, `savings`, dan `loans`.
- Menghitung total simpanan anggota.
- Menghitung total pinjaman anggota.
- Menghitung saldo bersih anggota.
- Mengurutkan anggota berdasarkan saldo bersih tertinggi.

Contoh loan approval rate:

```java
@Query(value = """
    SELECT 
        m.id,
        m.name,
        COUNT(l.id) AS total_loans,
        SUM(CASE WHEN l.status = 'APPROVED' THEN 1 ELSE 0 END) AS approved_loans,
        ROUND(
            (SUM(CASE WHEN l.status = 'APPROVED' THEN 1 ELSE 0 END) * 100.0)
            / NULLIF(COUNT(l.id),0),
        2) AS approval_rate
    FROM members m
    LEFT JOIN loans l ON m.id = l.member_id
    GROUP BY m.id, m.name
""", nativeQuery = true)
List<Object[]> getLoanApprovalRate();
```

Query ini tergolong advanced karena menggunakan:

- `LEFT JOIN`
- `COUNT`
- `SUM`
- `CASE WHEN`
- `ROUND`
- `NULLIF`
- `GROUP BY`
- `HAVING`
- `ORDER BY`
- `LIMIT`

Native SQL dipilih agar perhitungan dashboard dapat dilakukan langsung di database dan tidak perlu menghitung seluruh data secara manual di Java.

### 4. Containerization & Microservices

Project ini sudah mendukung containerization menggunakan Docker. Aplikasi Spring Boot, PostgreSQL, dan Redis dijalankan sebagai service terpisah melalui `docker-compose.yml`. Dengan pendekatan ini, environment development menjadi lebih konsisten karena dependency utama tidak perlu diinstal manual di komputer lokal.

File containerization yang digunakan:

```text
Dockerfile
.dockerignore
docker-compose.yml
```

#### Dockerfile

`Dockerfile` menggunakan pola multi-stage build. Tahap pertama melakukan build aplikasi menggunakan Maven Wrapper, sedangkan tahap kedua menjalankan file `.jar` menggunakan image JRE yang lebih ringan. Pola ini membuat image aplikasi lebih bersih dan tidak membawa dependency build yang tidak diperlukan saat runtime.

```dockerfile
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw -q -DskipTests dependency:go-offline
COPY src ./src
RUN ./mvnw -q -DskipTests clean package

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "app.jar"]
```

#### Docker Compose

`docker-compose.yml` mendefinisikan tiga service utama:

| Service | Container | Fungsi | Port |
|---|---|---|---:|
| `app` | `koperasi-app` | Menjalankan aplikasi Spring Boot | 8085 |
| `postgres` | `koperasi-postgres` | Database utama aplikasi | 5432 |
| `redis` | `koperasi-redis` | Cache untuk data dashboard/query berat | 6379 |

Aplikasi di dalam container tidak menggunakan `localhost` untuk mengakses database dan Redis, tetapi menggunakan nama service Docker Compose, yaitu `postgres` dan `redis`. Nilai tersebut dikirim melalui environment variable:

```yaml
SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/koperasi_db
SPRING_DATA_REDIS_HOST: redis
```

#### Menjalankan aplikasi dengan Docker

```bash
docker compose up -d --build
```

Cek container yang berjalan:

```bash
docker ps
```

Cek log aplikasi:

```bash
docker logs -f koperasi-app
```

Akses aplikasi:

```text
http://localhost:8085
```

Menghentikan semua container:

```bash
docker compose down
```

Menghapus container sekaligus volume database/cache:

```bash
docker compose down -v
```

### 5. Redis dan Caching Strategy

Redis digunakan sebagai cache untuk menyimpan hasil query dashboard yang relatif berat. Dashboard biasanya berisi agregasi data, join beberapa tabel, dan perhitungan statistik. Jika query dashboard dipanggil berulang kali, aplikasi tidak perlu selalu membaca langsung dari PostgreSQL.

Caching diaktifkan pada class utama:

```java
@EnableCaching
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
```

Konfigurasi Redis cache berada di `RedisConfig.java`:

```java
@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration config =
                RedisCacheConfiguration.defaultCacheConfig()
                        .serializeKeysWith(
                                RedisSerializationContext.SerializationPair
                                        .fromSerializer(new StringRedisSerializer()))
                        .serializeValuesWith(
                                RedisSerializationContext.SerializationPair
                                        .fromSerializer(serializer));

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build();
    }
}
```

Strategi caching yang digunakan:

#### a. Cacheable untuk data dashboard

Method dashboard diberi anotasi `@Cacheable`.

Contoh:

```java
@Cacheable(value = "dashboard:financial", key = "'financial'")
public List<MemberFinancialResponse> getFinancialReport() {
    return repository.getMemberFinancialReport()
            .stream()
            .map(...)
            .toList();
}
```

Artinya:

- Request pertama mengambil data dari PostgreSQL.
- Hasilnya disimpan ke Redis.
- Request berikutnya dengan key yang sama mengambil data dari Redis.
- Response menjadi lebih cepat karena tidak selalu menjalankan query agregasi ulang.

#### b. Cache eviction saat data berubah

Saat data simpanan atau pinjaman dibuat, cache dashboard dihapus agar data dashboard tidak stale.

Contoh pada `SavingServiceImpl`:

```java
@CacheEvict(value = {
        "dashboard:summary",
        "dashboard:financial",
        "dashboard:active-members",
        "dashboard:risk-members"
}, allEntries = true)
public SavingResponse create(SavingRequest request) {
    ...
}
```

Contoh pada `LoanServiceImpl`:

```java
@CacheEvict(value = {
        "dashboard:summary",
        "dashboard:financial",
        "dashboard:active-members",
        "dashboard:risk-members"
}, allEntries = true)
public LoanResponse create(LoanRequest request) {
    ...
}
```

Alur caching:

```text
GET dashboard pertama
        ↓
Query PostgreSQL
        ↓
Simpan hasil ke Redis
        ↓
GET dashboard berikutnya
        ↓
Ambil dari Redis
```

Alur cache eviction:

```text
POST saving / POST loan
        ↓
Data PostgreSQL berubah
        ↓
Cache dashboard dihapus
        ↓
GET dashboard berikutnya query ulang ke PostgreSQL
        ↓
Hasil baru disimpan lagi ke Redis
```

Strategi ini cocok untuk dashboard karena data dashboard dibaca lebih sering daripada ditulis.

## Catatan Pengembangan

Beberapa hal yang dapat dikembangkan berikutnya:

- Menambahkan validasi request menggunakan `@Valid` dan anotasi validasi pada DTO.
- Menambahkan global exception handler.
- Menambahkan response wrapper standar.
- Menambahkan pagination pada endpoint list.
- Menambahkan unit test dan integration test.
- Menambahkan TTL pada Redis cache agar cache otomatis kedaluwarsa.
- Menambahkan Dockerfile untuk menjalankan aplikasi Spring Boot sebagai container.
