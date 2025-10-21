
const STORAGE_KEY = "basic_auth_token"; 

export function setAuth(username, password, persist = false) {
  // store base64 token; choose sessionStorage (default) or localStorage if persist=true
  const token = btoa(`${username}:${password}`);
  if (persist) {
    localStorage.setItem(STORAGE_KEY, token);
    sessionStorage.removeItem(STORAGE_KEY);
  } else {
    sessionStorage.setItem(STORAGE_KEY, token);
    localStorage.removeItem(STORAGE_KEY);
  }
  return token;
}

export function getAuth() {
  return sessionStorage.getItem(STORAGE_KEY) || localStorage.getItem(STORAGE_KEY) || null;
}

export function clearAuthStorage() {
  sessionStorage.removeItem(STORAGE_KEY);
  localStorage.removeItem(STORAGE_KEY);
}
