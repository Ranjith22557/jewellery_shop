# Deploying Murugan Jewellery — Free Hosting Guide

Your stack: Spring Boot (Java 17) + PostgreSQL + Firebase Auth.

**Recommended free combo:** Render (app hosting) + Neon (PostgreSQL database)
— both have genuinely free tiers with no credit card required, and work well together.

---

## 0. Before you deploy — code fixes already applied

Two things in the code would have broken on any cloud host, and are already fixed in this
package:

1. **Firebase credentials** were loaded from a local file path (`src/main/resources/jewellery.json`)
   that only exists on your machine. The app now also accepts credentials via an environment
   variable (`FIREBASE_SERVICE_ACCOUNT_JSON`) — see step 3 below.
2. **Server port** was hardcoded to 8080. Render (and most hosts) assign a dynamic port via a
   `PORT` environment variable — the app now respects it automatically.

---

## 1. Push your code to GitHub

Render and Neon both deploy from a GitHub repo.

```bash
git add .
git commit -m "Prepare for deployment"
git push origin main
```

Make sure `.env` and `jewellery.json` are **not** committed (they should already be in
`.gitignore`) — you'll paste their values into each platform's dashboard instead.

---

## 2. Create the free database on Neon

1. Go to [neon.tech](https://neon.tech) → sign up (no card needed)
2. Create a new project (choose a region close to you)
3. Copy the **connection string** it gives you — it looks like:
   ```
   postgresql://username:password@ep-xxxx.region.aws.neon.tech/dbname?sslmode=require
   ```
4. From that string, note down separately:
   - **DB_URL**: `jdbc:postgresql://ep-xxxx.region.aws.neon.tech/dbname?sslmode=require`
     (same host/db, just with the `jdbc:` prefix and `postgresql://user:pass@` part removed)
   - **DB_USERNAME**: the username part
   - **DB_PASSWORD**: the password part

Neon's free tier suspends the database after a few minutes of inactivity and wakes up
automatically on the next request (small delay), which is fine for a small shop app.

---

## 3. Deploy the backend on Render

1. Go to [render.com](https://render.com) → sign up → **New → Web Service**
2. Connect your GitHub repo
3. Configure:
   - **Runtime:** Java
   - **Build Command:** `./mvnw clean package -DskipTests`
   - **Start Command:** `java -Xmx400m -jar target/*.jar`
     (the `-Xmx400m` caps memory usage — Render's free tier only gives 512MB RAM, and Spring
     Boot can run out of memory without this)
4. Add these **Environment Variables** (Render dashboard → Environment):

   | Key | Value |
   |---|---|
   | `DB_URL` | the `jdbc:postgresql://...` string from Neon |
   | `DB_USERNAME` | from Neon |
   | `DB_PASSWORD` | from Neon |
   | `FIREBASE_SERVICE_ACCOUNT_JSON` | paste the **entire contents** of your local `jewellery.json` file as one value |

5. Click **Deploy**. First build takes a few minutes.

**Free tier limitation:** Render's free web services sleep after ~15 minutes of no traffic, and
the next visit takes 30+ seconds to wake up (cold start). This is normal on the free tier — fine
for a personal/small-business app, not ideal for something needing instant availability.

---

## 4. Tell Firebase about your new domain

Firebase Auth blocks sign-ins from domains it doesn't recognize.

1. Go to [Firebase Console](https://console.firebase.google.com) → your project → **Authentication → Settings → Authorized domains**
2. Add your Render URL (e.g. `your-app-name.onrender.com`)

Without this step, login/signup will fail on the deployed site even though it works locally.

---

## 5. Test it

Visit your Render URL (`https://your-app-name.onrender.com`) and confirm:
- Signup/login work
- Data saves and loads (confirms the Neon DB connection is correct)
- All pages render correctly on mobile/tablet/desktop

---

## Alternatives worth knowing about

- **Railway** — similar to Render, usage-based free credits monthly, generally faster cold
  starts, but does require a card on file.
- **Koyeb** — free tier with built-in Postgres option (app + DB in one place).
- **Supabase** — if you'd rather have a dashboard for managing your data (adds email/auth
  features too, but you're already using Firebase for that so you'd just use it as a plain
  Postgres host).

All free tiers come with limitations (sleep/cold starts, storage caps, limited compute) — fine
for a small shop app or portfolio project, but worth upgrading to a paid tier once you have real
customers depending on uptime.
