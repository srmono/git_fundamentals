**React CRUD app with api** using:

* **React 18**
* **Axios with a separate config file**
* **React Router v6**
* **JSONPlaceholder** API
* **Full directory structure**
* **All code in code blocks**

---

## Directory Structure

```
react-crud-jsonplaceholder/
├── public/
│   └── index.html
├── src/
│   ├── api/
│   │   └── axiosConfig.js
│   ├── components/
│   │   ├── Navbar.js
│   │   ├── UserForm.js
│   │   └── UserList.js
│   ├── pages/
│   │   ├── AddUserPage.js
│   │   ├── EditUserPage.js
│   │   └── HomePage.js
│   ├── App.js
│   └── index.js
├── package.json
└── README.md
```

---

## 1. `src/api/axiosConfig.js`

```js
import axios from "axios";

const api = axios.create({
  baseURL: "https://jsonplaceholder.typicode.com",
  headers: {
    "Content-Type": "application/json",
  },
});

export default api;
```

---

## 2. `src/components/Navbar.js`

```jsx
import React from "react";
import { Link } from "react-router-dom";

const Navbar = () => (
  <nav style={{ padding: "1rem", borderBottom: "1px solid #ccc" }}>
    <Link to="/">Home</Link> | <Link to="/add">Add User</Link>
  </nav>
);

export default Navbar;
```

---

## 3. `src/components/UserForm.js`

```jsx
import React, { useState, useEffect } from "react";

const UserForm = ({ initialUser = { name: "", email: "" }, onSubmit, buttonText }) => {
  const [name, setName] = useState(initialUser.name);
  const [email, setEmail] = useState(initialUser.email);

  useEffect(() => {
    setName(initialUser.name || "");
    setEmail(initialUser.email || "");
  }, [initialUser]);

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit({ name, email });
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label>Name: </label>
        <input
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
          type="text"
        />
      </div>
      <div>
        <label>Email: </label>
        <input
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
          type="email"
        />
      </div>
      <button type="submit">{buttonText}</button>
    </form>
  );
};

export default UserForm;
```

---

## 4. `src/components/UserList.js`

```jsx
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import api from "../api/axiosConfig";

const UserList = () => {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchUsers = async () => {
    try {
      const response = await api.get("/users");
      setUsers(response.data);
    } catch (error) {
      console.error("Error fetching users:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  const handleDelete = async (id) => {
    try {
      await api.delete(`/users/${id}`);
      setUsers((prev) => prev.filter((user) => user.id !== id));
    } catch (error) {
      console.error("Error deleting user:", error);
    }
  };

  if (loading) return <p>Loading...</p>;

  return (
    <div>
      <h2>User List</h2>
      {users.map((user) => (
        <div key={user.id} style={{ marginBottom: "1rem", border: "1px solid #ccc", padding: "0.5rem" }}>
          <p>{user.name} ({user.email})</p>
          <Link to={`/edit/${user.id}`}>Edit</Link>
          <button onClick={() => handleDelete(user.id)} style={{ marginLeft: "0.5rem" }}>Delete</button>
        </div>
      ))}
    </div>
  );
};

export default UserList;
```

---

## 5. `src/pages/HomePage.js`

```jsx
import React from "react";
import UserList from "../components/UserList";

const HomePage = () => (
  <div style={{ padding: "1rem" }}>
    <UserList />
  </div>
);

export default HomePage;
```

---

## 6. `src/pages/AddUserPage.js`

```jsx
import React from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axiosConfig";
import UserForm from "../components/UserForm";

const AddUserPage = () => {
  const navigate = useNavigate();

  const handleAddUser = async (user) => {
    try {
      await api.post("/users", user);
      navigate("/");
    } catch (error) {
      console.error("Error adding user:", error);
    }
  };

  return (
    <div style={{ padding: "1rem" }}>
      <h2>Add User</h2>
      <UserForm onSubmit={handleAddUser} buttonText="Add" />
    </div>
  );
};

export default AddUserPage;
```

---

## 7. `src/pages/EditUserPage.js`

```jsx
import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import api from "../api/axiosConfig";
import UserForm from "../components/UserForm";

const EditUserPage = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const response = await api.get(`/users/${id}`);
        setUser(response.data);
      } catch (error) {
        console.error("Error fetching user:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchUser();
  }, [id]);

  const handleUpdateUser = async (updatedUser) => {
    try {
      await api.put(`/users/${id}`, updatedUser);
      navigate("/");
    } catch (error) {
      console.error("Error updating user:", error);
    }
  };

  if (loading) return <p>Loading user...</p>;
  if (!user) return <p>User not found</p>;

  return (
    <div style={{ padding: "1rem" }}>
      <h2>Edit User</h2>
      <UserForm
        initialUser={user}
        onSubmit={handleUpdateUser}
        buttonText="Update"
      />
    </div>
  );
};

export default EditUserPage;
```

---

## 8. `src/App.js`

```jsx
import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import Navbar from "./components/Navbar";
import HomePage from "./pages/HomePage";
import AddUserPage from "./pages/AddUserPage";
import EditUserPage from "./pages/EditUserPage";

const App = () => (
  <Router>
    <Navbar />
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/add" element={<AddUserPage />} />
      <Route path="/edit/:id" element={<EditUserPage />} />
    </Routes>
  </Router>
);

export default App;
```

---

## 9. `src/index.js`

```jsx
import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(<App />);
```

---

## 10. `public/index.html`

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>React CRUD with JSONPlaceholder</title>
  </head>
  <body>
    <div id="root"></div>
  </body>
</html>
```

---

## 11. `package.json` (dependencies snippet)

```json
{
  "name": "react-crud-jsonplaceholder",
  "version": "1.0.0",
  "private": true,
  "dependencies": {
    "axios": "^1.6.0",
    "react": "^18.2.0",
    "react-dom": "^18.2.0",
    "react-router-dom": "^6.14.2"
  },
  "scripts": {
    "start": "react-scripts start"
  }
}
```

---

## To Run This Project

```bash
npx create-react-app react-crud
```


-jsonplaceholder
cd react-crud-jsonplaceholder

# Install dependencies

npm install axios react-router-dom

# Replace src/ and public/ with the code above

# Start the app

npm start

```

