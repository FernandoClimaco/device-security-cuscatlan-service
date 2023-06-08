#device-security-cuscatlan-service 
>is to register new users, and generate tokens. It must be connected to a MYSQL database, and also to REDIS CACHE we are going to store the information of the users who make Login. Previously, the data had to be stored in MYSQL, the complete user record

#Next I will place the CURLS to use to be able to occupy the service:


>STEP 1: we need to register:

```curl --location --request POST 'Localhost:2023/login/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "Fernando",
    "password": "Fernando",
    "name": "Fernando Climaco",
    "identification": "05570911-2",
    "address": "Rosario,La paz.",
    "country" : "el salvador",
    "phone": "7777-7777",
    "email" : "fernandoaclimaco@hotmail.com"
}'
```


>STEP 2: we need to LOGIN for generate token use all transaction:

```curl --location --request POST 'http://localhost:2023/login/authenticate' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "Fernando",
    "password": "Fernando"
}'

```

>STEP 3: we can check the validity of the state of the TOKEN:

```curl --location --request POST 'localhost:2023/auth/validation' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4NjI0MDE4OSwiaWF0IjoxNjg2MjM2NTg5fQ.ulVimWlP_xwHe5_8614i5S3GYUUgyNUkHJfbCG9emQ0Ll-rsqcsyWelRztovcEe3yShMrqWAjgKSCQ7pWE2J6w'


