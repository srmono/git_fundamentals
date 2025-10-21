import axios from "axios";
import { getAuth, clearAuthStorage } from "../service/authService";


const axiosInstance = axios.create({
  baseURL: process.env.REACT_APP_API_BASE_URL || "http://localhost:8080",
  timeout: 10000,
});


axiosInstance.interceptors.request.use((config) => {

  const auth = getAuth();

  if (auth && !config.headers?.Authorization) {
    config.headers = config.headers || {};
    config.headers.Authorization = `Basic ${auth}`;

  }
  return config;
});

//to handle 401 unauthorized
axiosInstance.interceptors.response.use(

  (res) => res,

  (error) => {
    if (error.response && error.response.status === 401) {
      
      clearAuthStorage();
      
      window.location.reload();
    }
    return Promise.reject(error);
  }
);

export default axiosInstance;
