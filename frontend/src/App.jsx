import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'
import { AuthProvider, useAuth } from './auth/AuthContext'
import { hasAccess, NAV_ITEMS } from './auth/roles'
import Layout from './components/Layout'
import Login from './pages/Login'
import Dashboard from './pages/Dashboard'
import Users from './pages/Users'
import Inventory from './pages/Inventory'
import Categories from './pages/Categories'
import Vehicles from './pages/Vehicles'
import Compatibility from './pages/Compatibility'
import PurchaseOrders from './pages/PurchaseOrders'
import Suppliers from './pages/Suppliers'
import Sales from './pages/Sales'
import Reports from './pages/Reports'
import Settings from './pages/Settings'

function Guard({ children, roles }) {
  const { user } = useAuth()
  if (!user) return <Navigate to="/login" replace />
  if (roles && !hasAccess(user.role, roles)) {
    return <Navigate to="/" replace />
  }
  return children
}

function AppRoutes() {
  const { user } = useAuth()
  const rolesFor = (path) => NAV_ITEMS.find((item) => item.path === path)?.roles

  return (
    <Routes>
      <Route path="/login" element={user ? <Navigate to="/" replace /> : <Login />} />
      <Route element={<Guard><Layout /></Guard>}>
        <Route path="/" element={<Dashboard />} />
        <Route path="/users" element={<Guard roles={rolesFor('/users')}><Users /></Guard>} />
        <Route path="/inventory" element={<Guard roles={rolesFor('/inventory')}><Inventory /></Guard>} />
        <Route path="/categories" element={<Guard roles={rolesFor('/categories')}><Categories /></Guard>} />
        <Route path="/vehicles" element={<Guard roles={rolesFor('/vehicles')}><Vehicles /></Guard>} />
        <Route path="/compatibility" element={<Guard roles={rolesFor('/compatibility')}><Compatibility /></Guard>} />
        <Route path="/purchase-orders" element={<Guard roles={rolesFor('/purchase-orders')}><PurchaseOrders /></Guard>} />
        <Route path="/suppliers" element={<Guard roles={rolesFor('/suppliers')}><Suppliers /></Guard>} />
        <Route path="/sales" element={<Guard roles={rolesFor('/sales')}><Sales /></Guard>} />
        <Route path="/reports" element={<Guard roles={rolesFor('/reports')}><Reports /></Guard>} />
        <Route path="/settings" element={<Settings />} />
      </Route>
      <Route path="*" element={<Navigate to={user ? '/' : '/login'} replace />} />
    </Routes>
  )
}

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <AppRoutes />
      </BrowserRouter>
    </AuthProvider>
  )
}
