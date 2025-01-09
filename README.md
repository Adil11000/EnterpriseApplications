# Reservation Platform

## Setup, Run, and Try

1. `docker compose up`
2. Setup the frontend:
    - Navigate to the `frontend` directory
    - Run `npm install`
    - Run `npm run build`
    - Copy the `dist` directory to the `src/main/resources/` directory and rename it to `static`
3. Multiple options to run the Spring Boot project:
    - Open the Spring Boot project in IntelliJ IDEA and run the project
    - Run the project using the command `./mvnw spring-boot:run`
4. Open the browser and navigate to `http://localhost:8080/`

## Sources

### GitHub Copilot

Was used multiple times to autocomplete code snippets, Chat was not used.

### ChatGPT

- [Sample Data](https://chatgpt.com/share/6780181a-0e38-8002-8a63-4b786e0f08ff)
- [Recursion in Entity JSON](https://chatgpt.com/share/67801832-cc74-8002-9069-df57665beca8)
- [Serializable](https://chatgpt.com/share/6780184a-31bc-8002-8914-fa0e936c3d3f)
- [Svelte Frontend on Spring Boot](https://chatgpt.com/share/67801887-0884-8002-baa5-845a6d97cced)
- [Getting started with this project](https://chatgpt.com/share/677a9dc5-f8d8-8002-8ec1-b3b4aad9a5af)
- [Matching](https://chatgpt.com/share/678018ad-6ae8-8002-a21a-da5d0b614ca4)
- [Pagination](https://chatgpt.com/share/678018be-68bc-8002-bda0-7b3c2e3944c4)

### Websites
- [https://docs.spring.io/spring-boot/docs/2.0.0.M2/reference/html/boot-features-session.html](https://docs.spring.io/spring-boot/docs/2.0.0.M2/reference/html/boot-features-session.html)
- [https://docs.spring.io/spring-boot/docs/2.0.x/reference/html/boot-features-session.html](https://docs.spring.io/spring-boot/docs/2.0.x/reference/html/boot-features-session.html)
- [https://docs.spring.io/spring-session/reference/2.7/guides/boot-findbyusername.html](https://docs.spring.io/spring-session/reference/2.7/guides/boot-findbyusername.html)
- [https://docs.spring.io/spring-session/reference/2.7/guides/boot-jdbc.html](https://docs.spring.io/spring-session/reference/2.7/guides/boot-jdbc.html)
- [https://haithai91.medium.com/404-not-found-spring-boot-201657e15abe](https://haithai91.medium.com/404-not-found-spring-boot-201657e15abe)
- [https://jxausea.medium.com/spring-boot-integrated-spring-session-quick-start-demo-54b0619a7bd0](https://jxausea.medium.com/spring-boot-integrated-spring-session-quick-start-demo-54b0619a7bd0)
- [https://malshani-wijekoon.medium.com/spring-boot-folder-structure-best-practices-18ef78a81819](https://malshani-wijekoon.medium.com/spring-boot-folder-structure-best-practices-18ef78a81819)
- [https://medium.com/all-about-tech-and-techies/implementing-user-authentication-in-a-spring-boot-application-a-detailed-step-by-step-guide-b15a9877569b](https://medium.com/all-about-tech-and-techies/implementing-user-authentication-in-a-spring-boot-application-a-detailed-step-by-step-guide-b15a9877569b)
- [https://medium.com/@barisalgun/spring-boot-pagination-524083e699fc](https://medium.com/@barisalgun/spring-boot-pagination-524083e699fc)
- [https://medium.com/@itzgeoff/including-react-in-your-spring-boot-maven-build-ae3b8f8826e](https://medium.com/@itzgeoff/including-react-in-your-spring-boot-maven-build-ae3b8f8826e)
- [https://medium.com/@kalpadiptya.roy/nobody-will-tell-you-this-about-packaging-spring-boot-and-react-together-ff8a1805bf2e](https://medium.com/@kalpadiptya.roy/nobody-will-tell-you-this-about-packaging-spring-boot-and-react-together-ff8a1805bf2e)
- [https://medium.com/@Lakshitha_Fernando/spring-security-6-and-spring-boot-3-with-simple-project-91389cc13119](https://medium.com/@Lakshitha_Fernando/spring-security-6-and-spring-boot-3-with-simple-project-91389cc13119)
- [https://medium.com/version-1/db-migrations-in-spring-boot-7d48e5e18738](https://medium.com/version-1/db-migrations-in-spring-boot-7d48e5e18738)
- [https://medium.com/@ZiaurrahmanAthaya/how-to-create-custom-seeder-in-spring-boot-bbf73ffb8d84](https://medium.com/@ZiaurrahmanAthaya/how-to-create-custom-seeder-in-spring-boot-bbf73ffb8d84)
- [https://medium.com/@ZiaurrahmanAthaya/how-to-create-session-authentication-using-spring-boot-801320adcd26](https://medium.com/@ZiaurrahmanAthaya/how-to-create-session-authentication-using-spring-boot-801320adcd26)
- [https://spring.io/guides/gs/spring-boot](https://spring.io/guides/gs/spring-boot)
- [https://stackoverflow.com/questions/31318107/spring-boot-cannot-access-rest-controller-on-localhost-404](https://stackoverflow.com/questions/31318107/spring-boot-cannot-access-rest-controller-on-localhost-404)
- [https://stackoverflow.com/questions/32201441/how-do-i-retrieve-query-parameters-in-a-spring-boot-controller](https://stackoverflow.com/questions/32201441/how-do-i-retrieve-query-parameters-in-a-spring-boot-controller)
- [https://stackoverflow.com/questions/36809528/spring-boot-cors-filter-cors-preflight-channel-did-not-succeed](https://stackoverflow.com/questions/36809528/spring-boot-cors-filter-cors-preflight-channel-did-not-succeed)
- [https://stackoverflow.com/questions/42393211/how-can-i-serve-static-html-from-spring-boot](https://stackoverflow.com/questions/42393211/how-can-i-serve-static-html-from-spring-boot)
- [https://stackoverflow.com/questions/51930947/httpsession-in-springboot](https://stackoverflow.com/questions/51930947/httpsession-in-springboot)
- [https://stackoverflow.com/questions/52927910/spring-boot-how-to-handle-page-not-found-exception-using-exception-handler](https://stackoverflow.com/questions/52927910/spring-boot-how-to-handle-page-not-found-exception-using-exception-handler)
- [https://stackoverflow.com/questions/55500598/configuring-cors-in-spring-boot-application-bean-corsconfigurationsource-doesnt](https://stackoverflow.com/questions/55500598/configuring-cors-in-spring-boot-application-bean-corsconfigurationsource-doesnt)
- [https://stackoverflow.com/questions/56927217/how-to-enable-cors-in-spring-boot-not-working](https://stackoverflow.com/questions/56927217/how-to-enable-cors-in-spring-boot-not-working)
- [https://stackoverflow.com/questions/57738669/what-is-the-default-session-storage-for-spring-boot](https://stackoverflow.com/questions/57738669/what-is-the-default-session-storage-for-spring-boot)
- [https://stackoverflow.com/questions/58671257/error-starting-spring-boot-an-attempt-was-made-to-call-a-method-that-does-not-e](https://stackoverflow.com/questions/58671257/error-starting-spring-boot-an-attempt-was-made-to-call-a-method-that-does-not-e)
- [https://stackoverflow.com/questions/63138664/how-to-make-intellij-run-a-spring-boot-app-with-the-maven-profile-its-built-wit](https://stackoverflow.com/questions/63138664/how-to-make-intellij-run-a-spring-boot-app-with-the-maven-profile-its-built-wit)
- [https://stackoverflow.com/questions/67418154/java-lang-unsupportedoperationexception-remove](https://stackoverflow.com/questions/67418154/java-lang-unsupportedoperationexception-remove)
- [https://stackoverflow.com/questions/69279535/java-spring-boot-how-to-seed-a-database-like-in-php-laravel](https://stackoverflow.com/questions/69279535/java-spring-boot-how-to-seed-a-database-like-in-php-laravel)
- [https://stackoverflow.com/questions/72990251/how-to-get-value-of-query-param-array-in-spring-boot](https://stackoverflow.com/questions/72990251/how-to-get-value-of-query-param-array-in-spring-boot)
- [https://stackoverflow.com/questions/73048483/spring-boot-build-image-with-npm-installed](https://stackoverflow.com/questions/73048483/spring-boot-build-image-with-npm-installed)
- [https://stackoverflow.com/questions/76987080/cors-in-spring-security-spring-boot-3](https://stackoverflow.com/questions/76987080/cors-in-spring-security-spring-boot-3)
- [https://stackoverflow.com/questions/77545551/error-illegalstateexception-no-target-validator-set-after-upgrade-from-spring](https://stackoverflow.com/questions/77545551/error-illegalstateexception-no-target-validator-set-after-upgrade-from-spring)
- [https://stackoverflow.com/questions/77598826/spring-boot-jpa-jakarta-not-fetching-manytomany-data](https://stackoverflow.com/questions/77598826/spring-boot-jpa-jakarta-not-fetching-manytomany-data)
- [https://www.geeksforgeeks.org/spring-boot-session-management/](https://www.geeksforgeeks.org/spring-boot-session-management/)
