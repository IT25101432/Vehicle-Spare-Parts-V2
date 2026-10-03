import axios from 'axios';

// Create Axios instance pointing to your Spring Boot backend
const api = axios.create({
    baseURL: 'http://localhost:8080/api', // Adjust base path to match your Spring Boot context/controllers
    headers: {
        'Content-Type': 'application/json',
    },
});

// Request Interceptor: Attach JWT Token if available
api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

// Response Interceptor: Handle Unauthorized/Expired tokens
api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response && error.response.status === 401) {
            // Handle logout or redirect to login page if token expires
            localStorage.removeItem('token');
            window.location.href = '/login';
        }
        return Promise.reject(error);
    }
);

export default api;