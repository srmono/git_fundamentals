**React CRUD (Create, Read, Update, Delete) application** using:

* **Node.js 16** (works with latest React versions like 18+)
* **Latest React version (React 18 or later)**
* **Local data only** (no JSON server or backend)
* **Axios (though it’s usually for HTTP requests, not necessary for local state)**

 **multi-component structure**, **CRUD logic**, **local data**, and **React Router for routing**

---

## 1. Compatibility: Node 16 + Latest React

Node 16 works fine with the latest React (`react@18.2.0`, `react-dom@18.2.0`).

You can create a new React app with:

```bash
npx create-react-app react-crud-app
cd react-crud-app
npm install react-router-dom
```

---

## 2. App Structure (Components & Routing)

```plaintext
src/
├── components/
│   ├── CustomerList.js
│   ├── CustomerForm.js
│   ├── CustomerDetails.js
│   └── Navbar.js
├── pages/
│   ├── Home.js
│   ├── AddCustomer.js
│   └── EditCustomer.js
├── App.js
└── index.js
```

---

## 3. CRUD with Local Data

Let’s create a local array and manage it using `useState`. We will also use `React Router` for navigation.

---

### 🔹 `App.js` — Setup routing and state

```jsx
import React, { useState } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import AddCustomer from "./pages/AddCustomer";
import EditCustomer from "./pages/EditCustomer";
import Navbar from "./components/Navbar";

function App() {
  const [customers, setCustomers] = useState([
    { id: 1, name: "John Doe", email: "john@example.com" },
  ]);

  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home customers={customers} setCustomers={setCustomers} />} />
        <Route path="/add" element={<AddCustomer setCustomers={setCustomers} />} />
        <Route path="/edit/:id" element={<EditCustomer customers={customers} setCustomers={setCustomers} />} />
      </Routes>
    </Router>
  );
}

export default App;
```

---

### 🔹 `components/Navbar.js`

```jsx
import React from "react";
import { Link } from "react-router-dom";

const Navbar = () => (
  <nav>
    <Link to="/">Home</Link> | <Link to="/add">Add Customer</Link>
  </nav>
);

export default Navbar;
```

---

### 🔹 `pages/Home.js` — Read & Delete

```jsx
import React from "react";
import { Link } from "react-router-dom";

const Home = ({ customers, setCustomers }) => {
  const handleDelete = (id) => {
    const filtered = customers.filter(c => c.id !== id);
    setCustomers(filtered);
  };

  return (
    <div>
      <h2>Customer List</h2>
      {customers.map((c) => (
        <div key={c.id}>
          <p>{c.name} - {c.email}</p>
          <Link to={`/edit/${c.id}`}>Edit</Link>
          <button onClick={() => handleDelete(c.id)}>Delete</button>
        </div>
      ))}
    </div>
  );
};

export default Home;
```

---

### 🔹 `pages/AddCustomer.js` — Create

```jsx
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const AddCustomer = ({ setCustomers }) => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    const newCustomer = {
      id: Date.now(),
      name,
      email,
    };
    setCustomers(prev => [...prev, newCustomer]);
    navigate("/");
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Add Customer</h2>
      <input value={name} onChange={e => setName(e.target.value)} placeholder="Name" required />
      <input value={email} onChange={e => setEmail(e.target.value)} placeholder="Email" required />
      <button type="submit">Add</button>
    </form>
  );
};

export default AddCustomer;
```

---

### 🔹 `pages/EditCustomer.js` — Update

```jsx
import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";

const EditCustomer = ({ customers, setCustomers }) => {
  const { id } = useParams();
  const navigate = useNavigate();
  const customer = customers.find(c => c.id === Number(id));

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");

  useEffect(() => {
    if (customer) {
      setName(customer.name);
      setEmail(customer.email);
    }
  }, [customer]);

  const handleUpdate = (e) => {
    e.preventDefault();
    const updated = customers.map(c =>
      c.id === Number(id) ? { ...c, name, email } : c
    );
    setCustomers(updated);
    navigate("/");
  };

  if (!customer) return <p>Customer not found</p>;

  return (
    <form onSubmit={handleUpdate}>
      <h2>Edit Customer</h2>
      <input value={name} onChange={e => setName(e.target.value)} required />
      <input value={email} onChange={e => setEmail(e.target.value)} required />
      <button type="submit">Update</button>
    </form>
  );
};

export default EditCustomer;
```


---

## Summary

| Feature                   | Included? |
| ------------------------- | --------- |
| Node.js 16 Compatible     |         |
| Latest React (18+)        |         |
| Local data only           |         |
| CRUD operations           |         |
| React Router integration  |         |
| Multi-component structure |         |
| Component communication   |         |
| Axios support (optional)  |         |

