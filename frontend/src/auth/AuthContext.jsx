import { createContext, useContext, useMemo, useState } from 'react'
import api from '../api/client'
import { normalizeRole } from './roles'

const AuthContext = createContext(null)

const STORAGE_KEY = 'lanka_auth'
const LEGACY_KEY = 'panze_auth'

function readSession() {
  const raw = localStorage.getItem(STORAGE_KEY)
  if (raw) return raw
  const legacy = localStorage.getItem(LEGACY_KEY)
  if (legacy) {
    localStorage.setItem(STORAGE_KEY, legacy)
    localStorage.removeItem(LEGACY_KEY)
  }
  return legacy
}

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => {
    const raw = readSession()
    return raw ? JSON.parse(raw) : null
  })

  const persist = (payload) => {
    const next = {
      token: payload.token,
      id: payload.id,
      username: payload.username,
      email: payload.email,
      role: normalizeRole(payload.role),
      firstName: payload.firstName,
      lastName: payload.lastName,
    }
    localStorage.setItem(STORAGE_KEY, JSON.stringify(next))
    localStorage.removeItem(LEGACY_KEY)
    setUser(next)
    return next
  }

  const login = async (username, password) => {
    const { data } = await api.post('/auth/login', { username, password })
    return persist(data)
  }

  const logout = () => {
    localStorage.removeItem(STORAGE_KEY)
    localStorage.removeItem(LEGACY_KEY)
    setUser(null)
  }

  const value = useMemo(() => ({ user, login, logout }), [user])
  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

export function useAuth() {
  const ctx = useContext(AuthContext)
  if (!ctx) throw new Error('useAuth must be used inside AuthProvider')
  return ctx
}
