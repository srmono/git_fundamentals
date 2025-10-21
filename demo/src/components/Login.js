import React, { useState } from "react";
import { setAuth } from "../service/authService";


export default function Login({ onLoginSuccess }) {

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const [persist, setPersist] = useState(false);// localstorage
  const [loading, setLoading] = useState(false); //loading?

  const [error, setError] = useState(null);

  async function handleSubmit(e) {
    e.preventDefault();
    setError(null);

    if (!username || !password) {
      setError("Username and password required");
      return;
    }
    try {
      setLoading(true);
      
      setAuth(username, password, persist);
      onLoginSuccess();
    } catch (err) {
      console.error("Login failed:", err);
      setError("Login failed");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="container py-5">
      <div className="mx-auto" style={{ maxWidth: 420 }}>
        <div className="card shadow-sm">
          <div className="card-body">
            <h5 className="card-title mb-3">Login</h5>

            {error && <div className="alert alert-danger">{error}</div>}

            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label className="form-label">Username</label>
                <input
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                  className="form-control"
                  autoComplete="username"
                />
              </div>

              <div className="mb-3">
                <label className="form-label">Password</label>
                <input
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  type="password"
                  className="form-control"
                  autoComplete="current-password"
                />
              </div>

              <div className="mb-3 form-check">
                <input
                  id="persist"
                  type="checkbox"
                  checked={persist}
                  onChange={(e) => setPersist(e.target.checked)}
                  className="form-check-input"
                />
                <label htmlFor="persist" className="form-check-label">
                  Remember me (persist)
                </label>
              </div>

              <button className="btn btn-primary w-100" disabled={loading}>
                {loading ? "Logging in…" : "Login"}
              </button>
            </form>
            <small className="text-muted d-block mt-3">
              For demo only — credentials stored in browser storage. Use tokens/HTTPS in production.
            </small>
          </div>
        </div>
      </div>
    </div>
  );
}
