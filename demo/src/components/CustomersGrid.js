import React, { useEffect, useState } from "react";
import { clearAuthStorage } from "../service/authService";
import { fetchCustomers } from "../service/customerService";


export default function CustomersGrid() {

  const [customers, setCustomers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    let mounted = true;
    setLoading(true);

    fetchCustomers()
      .then((data) => {
        if (!mounted) return;
        setCustomers(Array.isArray(data) ? data : []);
      })
      .catch((err) => {
        if (!mounted) return;
        console.error("Failed to load customers:", err);
        setError(err);
      })
      .finally(() => {
        if (!mounted) return;
        setLoading(false);
      });

    return () => {
      mounted = false;
    };
  }, []);

  const handleLogout = () => {
    clearAuthStorage();
    window.location.reload(); // show login form
  };

  if (loading) {
    return (
      <div className="d-flex justify-content-center align-items-center py-5">
        <div className="spinner-border" role="status" aria-hidden="true"></div>
        <span className="ms-2">Loading customers...</span>
      </div>
    );
  }

  if (error) {
    return (
      <div className="container py-3">
        <div className="d-flex justify-content-between align-items-center mb-3">
          <h2>Customers</h2>
          <button className="btn btn-outline-danger btn-sm" onClick={handleLogout}>
            Logout
          </button>
        </div>
        <div className="alert alert-danger">Could not load customers: {error.message || "Error"}</div>
      </div>
    );
  }

  return (
    <div className="container py-3">
      
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h2>Customers</h2>
        <button className="btn btn-outline-danger btn-sm" onClick={handleLogout}>
          Logout
        </button>
      </div>

      {customers.length === 0 ? (
        <div className="alert alert-info">No customers found.</div>
      ) : (
        <div className="row g-3">
          {customers.map((c) => {
            const fullName = `${c.firstName || ""} ${c.lastName || ""}`.trim();
            return (
              <div key={c.id} className="col-12 col-sm-6 col-md-4 col-lg-3">
                <div className="card h-100 shadow-sm">
                  <div className="card-body d-flex flex-column">
                    <h5 className="card-title">{fullName || "—"}</h5>
                    <h6 className="card-subtitle mb-2 text-muted">{c.role || "N/A"}</h6>

                    <p className="card-text mb-1">
                      <strong>Email:</strong><br />
                      <a href={`mailto:${c.email}`}>{c.email || "—"}</a>
                    </p>

                    <p className="card-text mb-1">
                      <strong>Phone:</strong><br />{c.phone || "—"}
                    </p>

                    <p className="card-text mb-2">
                      <strong>Active:</strong>{" "}
                      {String(c.active) === "true" ? (
                        <span className="badge bg-success">Yes</span>
                      ) : (
                        <span className="badge bg-secondary">No</span>
                      )}
                    </p>

                    <div className="mt-auto">
                      <small className="text-muted">
                        Accounts: {Array.isArray(c.accounts) ? c.accounts.length : 0}
                      </small>
                      <div className="d-flex gap-2 mt-2">
                        <button className="btn btn-sm btn-outline-primary">View</button>
                        <button className="btn btn-sm btn-outline-secondary">Edit</button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
}
