# Mainichishinpo

Chạy với docker:

- xóa các image cũ: `sudo docker-compose down --rmi all`
- build lại image mới nhất:` sudo docker build -t mainichishinpo:latest .`
- Dựng docker compose : `sudo docker-compose up -d`
- kiểm tra các container đang chạy: `sudo docker ps`
- Truy cập địa chỉ localhost:8005 để kiểm tra kết quả 


thứ tự thực hiện:

- mockup, design UML
- web static
- registration
- add entity: post, comment, tag -> relationship table -> Mysql
- CRUD post with comment
- Search and Paging
- FE: preview image, confirm-password
- Docker, docker-compose
- Email: verification new account,,forgot password
- Schedule: https://github.com/TechMaster/SpringBootBasic/tree/main/schedule/01Basic/demoschedule
- i18n: https://levunguyen.com/laptrinhspring/2020/04/22/su-dung-da-ngon-ngu-trong-spring/#b%C6%B0%E1%BB%9Bc-2-c%E1%BA%A5u-h%C3%ACnh-localeresolver
