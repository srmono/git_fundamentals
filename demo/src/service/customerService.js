import axiosInstance from "../api/axiosInstance";

//axiosInstance

export function fetchCustomers(params = {}) {
  return axiosInstance.get("/api/customers", { params }).then(res => res.data);
}

export function fetchCustomerById(id) {
  return axiosInstance.get(`/api/customers/${id}`).then(res => res.data);
}

// createcustomer
// updatecustomer
// deletecustomer