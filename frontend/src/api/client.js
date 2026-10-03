import axios from 'axios'

const STORAGE_KEY = 'lanka_auth'
const LEGACY_KEY = 'panze_auth'

const readAuth = () => localStorage.getItem(STORAGE_KEY) || localStorage.getItem(LEGACY_KEY)

const api = axios.create({
  baseURL: '/api',
})

api.interceptors.request.use((config) => {
  const raw = readAuth()
  if (raw) {
    const { token } = JSON.parse(raw)
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
  }
  return config
})

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      const path = window.location.pathname
      if (path !== '/login') {
        localStorage.removeItem(STORAGE_KEY)
        localStorage.removeItem(LEGACY_KEY)
        window.location.assign('/login')
      }
    }
    return Promise.reject(error)
  },
)

export function apiError(error) {
  const data = error.response?.data
  if (typeof data === 'string' && data.trim()) return data
  return data?.message || data?.error || error.message || 'Something went wrong'
}

export default api
