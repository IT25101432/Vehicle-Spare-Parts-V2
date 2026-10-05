import { useEffect, useRef, useState } from 'react'
import { NavLink, Outlet, useLocation, useNavigate } from 'react-router-dom'
import { useAuth } from '../auth/AuthContext'
import { NAV_ITEMS, ALERT_ROLES, hasAccess, roleLabel } from '../auth/roles'
import api from '../api/client'
import { Icon } from './Icons'

const titles = {
  '/': 'Dashboard Overview',
  '/users': 'Staff Members',
  '/inventory': 'Inventory',
  '/categories': 'Categories',
  '/vehicles': 'Vehicles',
  '/compatibility': 'Compatibility',
  '/purchase-orders': 'Purchase Orders',
  '/suppliers': 'Suppliers',
  '/sales': 'Sales',
  '/reports': 'Sales Reports',
  '/settings': 'Settings',
}

export default function Layout() {
  const { user, logout } = useAuth()
  const location = useLocation()
  const navigate = useNavigate()
  const initials = `${user?.firstName?.[0] || user?.username?.[0] || 'U'}${user?.lastName?.[0] || ''}`.toUpperCase()

  const canSeeAlerts = hasAccess(user?.role, ALERT_ROLES)
  const [alerts, setAlerts] = useState([])
  const [bellOpen, setBellOpen] = useState(false)
  const bellRef = useRef(null)

  useEffect(() => {
    if (!canSeeAlerts) return undefined
    let alive = true
    const load = () => {
      api.get('/alerts')
        .then((res) => { if (alive && Array.isArray(res.data)) setAlerts(res.data) })
        .catch(() => {})
    }
    load()
    const timer = setInterval(load, 60000)
    return () => { alive = false; clearInterval(timer) }
  }, [canSeeAlerts])

  useEffect(() => {
    if (!bellOpen) return undefined
    const onDown = (event) => {
      if (bellRef.current && !bellRef.current.contains(event.target)) setBellOpen(false)
    }
    document.addEventListener('mousedown', onDown)
    return () => document.removeEventListener('mousedown', onDown)
  }, [bellOpen])

  const toggleBell = () => {
    setBellOpen((open) => {
      if (!open) {
        api.get('/alerts')
          .then((res) => { if (Array.isArray(res.data)) setAlerts(res.data) })
          .catch(() => {})
      }
      return !open
    })
  }

  const openAlert = (alert) => {
    setBellOpen(false)
    if (alert.path) navigate(alert.path)
  }

  // Hide alerts that point at pages this role cannot open (the Guard would bounce
  // them back to the dashboard), e.g. purchase-order alerts for a store keeper.
  const visibleAlerts = alerts.filter((alert) => {
    const item = NAV_ITEMS.find((navItem) => navItem.path === alert.path)
    return !item || hasAccess(user?.role, item.roles)
  })

  return (
    <div className="app-shell">
      <aside className="sidebar">
        <div className="brand">
          <strong>Lanka</strong>
          <span>Auto Parts</span>
        </div>
        <nav className="nav-list">
          {NAV_ITEMS.filter((item) => hasAccess(user?.role, item.roles)).map((item) => (
            <NavLink key={item.path} to={item.path} end={item.path === '/'} className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}>
              <Icon name={item.icon} />
              {item.label}
            </NavLink>
          ))}
        </nav>
        <div className="sidebar-footer">Auto parts operations</div>
      </aside>
      <section className="main">
        <header className="topbar">
          <h1>{titles[location.pathname] || 'Lanka Auto Parts'}</h1>
          <div className="top-actions">
            <button className="icon-btn" type="button" aria-label="Language">
              <Icon name="globe" />
            </button>
            {canSeeAlerts && (
              <div className="notif-wrap" ref={bellRef}>
                <button className="icon-btn" type="button" aria-label="Notifications" aria-expanded={bellOpen} onClick={toggleBell}>
                  <Icon name="bell" />
                  {visibleAlerts.length > 0 && <span className="notif-badge">{visibleAlerts.length > 99 ? '99+' : visibleAlerts.length}</span>}
                </button>
                {bellOpen && (
                  <div className="notif-panel">
                    <div className="notif-head">
                      <strong>Notifications</strong>
                      <span>{visibleAlerts.length} alert{visibleAlerts.length === 1 ? '' : 's'}</span>
                    </div>
                    <div className="notif-list">
                      {visibleAlerts.length === 0 ? (
                        <div className="notif-empty">You're all caught up</div>
                      ) : (
                        visibleAlerts.map((alert) => (
                          <button
                            key={alert.id}
                            type="button"
                            className={`notif-item ${alert.severity === 'CRITICAL' ? 'critical' : ''}`}
                            onClick={() => openAlert(alert)}
                          >
                            <span className={`notif-dot ${alert.severity === 'CRITICAL' ? 'critical' : ''}`} />
                            <span className="notif-body">
                              <strong>{alert.title}</strong>
                              <small>{alert.message}</small>
                            </span>
                          </button>
                        ))
                      )}
                    </div>
                  </div>
                )}
              </div>
            )}
            <div className="user-chip">
              <div className="avatar-fallback">{initials}</div>
              <div>
                <strong>{user?.firstName ? `${user.firstName} ${user.lastName || ''}`.trim() : user?.username}</strong>
                <small>{roleLabel(user?.role)}</small>
              </div>
              <button className="ghost-btn" type="button" onClick={logout}>Logout</button>
            </div>
          </div>
        </header>
        <Outlet />
      </section>
    </div>
  )
}
