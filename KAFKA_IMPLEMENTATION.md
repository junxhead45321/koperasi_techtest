# Kafka Implementation Notes

Kafka sudah diimplementasikan sebagai event publisher dan event consumer sederhana untuk domain koperasi.

## Topic

- `koperasi.member.events`
- `koperasi.saving.events`
- `koperasi.loan.events`

## Event yang Dikirim

- `MEMBER_CREATED`
- `MEMBER_UPDATED`
- `MEMBER_DELETED`
- `SAVING_CREATED`
- `LOAN_CREATED`
- `LOAN_STATUS_UPDATED`

## File yang Ditambahkan

```text
src/main/java/com/example/koperasi/config/KafkaConfig.java
src/main/java/com/example/koperasi/kafka/KafkaTopics.java
src/main/java/com/example/koperasi/kafka/KoperasiEventProducer.java
src/main/java/com/example/koperasi/kafka/KoperasiEventConsumer.java
src/main/java/com/example/koperasi/model/event/KoperasiEvent.java
```

## File yang Diubah

```text
src/main/resources/application.yaml
docker-compose.yml
src/main/java/com/example/koperasi/services/impl/MemberServiceImpl.java
src/main/java/com/example/koperasi/services/impl/SavingServiceImpl.java
src/main/java/com/example/koperasi/services/impl/LoanServiceImpl.java
```

## Cara Menjalankan

```bash
docker compose up -d
./mvnw spring-boot:run
```

Kafka UI dapat dibuka melalui:

```text
http://localhost:8088
```

## Cara Test Cepat

1. Jalankan container Kafka dan aplikasi.
2. Buat member melalui endpoint `POST /v1/api/members`.
3. Buat saving melalui endpoint `POST /v1/api/saving`.
4. Buat loan melalui endpoint `POST /v1/api/loans`.
5. Ubah status loan melalui endpoint `PUT /v1/api/loans/{loanId}/status?status=APPROVED`.
6. Cek log aplikasi atau Kafka UI untuk melihat event yang masuk ke topic.

## Catatan

Consumer saat ini hanya mencatat event ke log. Implementasi ini aman sebagai baseline sebelum event digunakan untuk proses lain seperti audit log, notification, sinkronisasi MongoDB, atau indexing Elasticsearch.
