FROM openjdk:8
VOLUME /temp
EXPOSE 8085
ADD ./target/service-product-0.0.1-SNAPSHOT.jar product.jar
ENTRYPOINT ["java","-jar","product.jar"]