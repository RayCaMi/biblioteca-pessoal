# Estágio 1: Build com Maven e JDK completo
FROM eclipse-temurin:17-jdk-jammy as build

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o descritor do projeto e baixa as dependências
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# O comando "go-offline" baixa todas as dependências de uma vez para otimizar o cache
RUN ./mvnw dependency:go-offline

# Copia o resto do código-fonte da aplicação
COPY src src

# Roda o build do Maven para compilar e empacotar a aplicação em um .jar
RUN ./mvnw package -DskipTests

# Estágio 2: Imagem final com JRE leve
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copia apenas o arquivo .jar gerado do estágio de build para a imagem final
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta que a aplicação usa
EXPOSE 8080

# Comando para executar a aplicação quando o contêiner iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]