# Deploying FinanceAll to Render + Neon (free)

The app and database are hosted separately:
- **App** → Render (free web service, built from `Dockerfile`)
- **Database** → Neon (free serverless Postgres)

The schema is created automatically by Flyway on first boot — no manual SQL.

---

## 1. Create the database (Neon)

1. Sign up at https://neon.tech (GitHub login, no card).
2. Create a project → it gives you a **connection string** like:
   ```
   postgresql://myuser:mypass@ep-cool-name-123456.us-east-2.aws.neon.tech/neondb?sslmode=require
   ```
3. Break it into the three values Render needs (note the **jdbc:** prefix and `?sslmode=require`):
   - `SPRING_DATASOURCE_URL`      = `jdbc:postgresql://ep-cool-name-123456.us-east-2.aws.neon.tech/neondb?sslmode=require`
   - `SPRING_DATASOURCE_USERNAME` = `myuser`
   - `SPRING_DATASOURCE_PASSWORD` = `mypass`

---

## 2. Deploy the app (Render)

1. Sign up at https://render.com (GitHub login, no card for free tier).
2. **New** → **Blueprint** → connect this repo. Render reads `render.yaml`.
   (Or: **New → Web Service → Docker** and configure manually.)
3. When prompted, fill in the secret env vars (they're marked `sync:false`):
   | Key | Value |
   |-----|-------|
   | `SPRING_DATASOURCE_URL` | the `jdbc:...?sslmode=require` URL from step 1 |
   | `SPRING_DATASOURCE_USERNAME` | Neon user |
   | `SPRING_DATASOURCE_PASSWORD` | Neon password |
   | `APP_ADMIN_PASSWORD` | a strong admin password |
   | `APP_ADMIN_RECOVERY_PIN` | a 6-digit PIN |
4. Click **Apply / Create**. First build takes a few minutes (Maven build runs in the container).

---

## 3. Verify

- Render shows the service URL, e.g. `https://financeall.onrender.com`
- Health: `https://financeall.onrender.com/actuator/health` → `{"status":"UP"}`
- App: open the URL → redirects to `/login`
- Log in with `admin` / the `APP_ADMIN_PASSWORD` you set

---

## Notes & gotchas

- **Cold starts:** the free web service sleeps after ~15 min idle. The next request takes ~30–60s to wake (Spring boot + spin-up). Normal for free tier.
- **Memory:** `render.yaml` sets `JAVA_OPTS` tuned for the 512 MB free instance (SerialGC, 55% heap). If you ever see OOM in logs, lower `MaxRAMPercentage` to `50.0`.
- **Neon SSL is required** — keep `?sslmode=require` in the JDBC URL or the connection fails.
- **Migrations:** Flyway runs `V1__init_schema.sql` automatically on first boot; `baseline-on-migrate` is on, so an already-populated DB is adopted safely.
