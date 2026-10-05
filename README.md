# Pedidos Tienda

[![docker-compose](https://github.com/gasparinxd/pedidos-tienda/actions/workflows/docker-compose.yml/badge.svg)](https://github.com/gasparinxd/pedidos-tienda/actions/workflows/docker-compose.yml)
[![devcontainer](https://github.com/gasparinxd/pedidos-tienda/actions/workflows/devcontainer.yml/badge.svg)](https://github.com/gasparinxd/pedidos-tienda/actions/workflows/devcontainer.yml)

Sistema base para la gestión de pedidos de una tienda.

- **backend/**: API REST con Spring Boot 4 (Java 21), PostgreSQL y RabbitMQ.
- **frontend/**: aplicación web en React (Vite), servida con Nginx en Docker.
- **docker-compose.yml**: levanta PostgreSQL, RabbitMQ, backend y frontend a la vez.

## Requisitos

- Docker y Docker Compose (o GitHub Codespaces, ver abajo)

Para desarrollo local sin Docker: Java 21+ y Node.js 22+.

## Levantar con Docker

```bash
docker compose up --build
```

| Servicio            | URL                                       |
|---------------------|-------------------------------------------|
| Frontend            | http://localhost:3000                     |
| Backend (API)       | http://localhost:8080/api/pedidos         |
| Health              | http://localhost:8080/actuator/health     |
| RabbitMQ Management | http://localhost:15672 (pedidos / pedidos)|
| PostgreSQL          | localhost:5432                            |

Los valores de BD, RabbitMQ y puertos se leen del archivo `.env` (no versionado). Si no existe, se usan los valores por defecto: usuario/contraseña `pedidos` y los puertos de la tabla.

Para detener: `docker compose down` (añade `-v` para borrar los datos).

## GitHub Codespaces

1. En GitHub: **Code → Codespaces → Create codespace on main**.
2. Al arrancar, el Codespace ejecuta `docker compose up -d --build` automáticamente (tarda 2–4 minutos la primera vez).
3. Abre la pestaña **Ports** y entra al puerto **3000 (Frontend)**. Si aparece *HTTP 401*, cambia la visibilidad del puerto a **Public**.

Si un Codespace se creó antes de un cambio en `.devcontainer.json`, usa **Codespaces: Rebuild Container** desde la paleta de comandos.

## Desarrollo local

```bash
# Infraestructura
docker compose up -d db rabbitmq

# Backend (http://localhost:8080)
cd backend
./mvnw spring-boot:run

# Frontend (http://localhost:5173, redirige /api al backend)
cd frontend
npm install
npm run dev
```

## API

| Método | Ruta                | Descripción       |
|--------|---------------------|-------------------|
| GET    | `/api/pedidos`      | Listar pedidos    |
| GET    | `/api/pedidos/{id}` | Obtener un pedido |
| POST   | `/api/pedidos`      | Registrar pedido  |

```bash
curl -X POST http://localhost:8080/api/pedidos \
  -H "Content-Type: application/json" \
  -d '{"cliente":"María","total":49.90}'
```

## Integración continua

- `docker-compose.yml` (workflow): ejecuta `mvnw package`, levanta los cuatro servicios y comprueba BD, RabbitMQ, API y frontend.
- `devcontainer.yml` (workflow): construye el devcontainer como lo hace Codespaces y verifica que el stack arranca solo.
