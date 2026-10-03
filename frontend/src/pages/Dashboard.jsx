import { useEffect, useMemo, useState } from 'react'
import { Bar, BarChart, CartesianGrid, Cell, Legend, Pie, PieChart, ResponsiveContainer, Tooltip, XAxis, YAxis } from 'recharts'
import api from '../api/client'
import { useAuth } from '../auth/AuthContext'
import { hasAccess, FULL_ACCESS, ROLES } from '../auth/roles'
import { Icon } from '../components/Icons'
import { money, statusBadge } from '../components/DataTable'

const COLORS = ['#86efac', '#60a5fa', '#f87171', '#c4b5fd', '#fbbf24']

export default function Dashboard() {
  const { user } = useAuth()
  const [sales, setSales] = useState([])
  const [parts, setParts] = useState([])
  const [orders, setOrders] = useState([])
  const [analytics, setAnalytics] = useState(null)
  const [vehicles, setVehicles] = useState([])
  const [mappings, setMappings] = useState([])
  const [categories, setCategories] = useState([])

  // Mirrors the backend SecurityConfig so a role is never shown (or asked for) data it cannot access.
  const canSales = hasAccess(user.role, [...FULL_ACCESS, ROLES.SALES_STAFF])
  const canParts = hasAccess(user.role, [...FULL_ACCESS, ROLES.PROCUREMENT_OFFICER, ROLES.STORE_KEEPER, ROLES.SALES_STAFF])
  const canPo = hasAccess(user.role, [...FULL_ACCESS, ROLES.PROCUREMENT_OFFICER])
  const canAnalytics = hasAccess(user.role, FULL_ACCESS)
  const canCatalog = hasAccess(user.role, [...FULL_ACCESS, ROLES.COMPATIBILITY_MANAGER])

  // Roles with no operational metrics (Compatibility Manager) see their own catalog metrics instead.
  const showCatalog = !(canSales || canParts || canPo) && canCatalog
  // Inventory value is derived from part prices + stock, so it follows parts access.
  const showChart = canParts
  const showPie = canParts
  const showInvoices = canSales
  const showStock = canParts || canPo

  useEffect(() => {
    const load = async () => {
      try {
        if (canSales) setSales((await api.get('/sales')).data || [])
        if (canParts) setParts((await api.get('/parts')).data || [])
        if (canPo) setOrders((await api.get('/po')).data || [])
        if (canAnalytics) setAnalytics((await api.get('/analytics')).data)
        if (showCatalog) {
          setVehicles((await api.get('/vehicles')).data || [])
          setMappings((await api.get('/mappings')).data || [])
          setCategories((await api.get('/categories')).data || [])
        }
      } catch {
        /* role-limited dashboards still render empty cards */
      }
    }
    load()
  }, [canSales, canParts, canPo, canAnalytics, showCatalog])

  const totalSales = sales.reduce((sum, sale) => sum + Number(sale.totalAmount || 0), 0)
  const totalPo = orders.reduce((sum, order) => sum + Number(order.totalAmount || 0), 0)
  const stockItems = parts.reduce((sum, part) => sum + Number(part.stockQuantity || 0), 0)
  const lowStock = parts.filter((part) => Number(part.stockQuantity) <= Number(part.reorderLevel || 0)).length
  // Total value of inventory on hand: every unit of every SKU, not one unit each.
  const inventoryValue = parts.reduce((sum, part) => sum + Number(part.price || 0) * Number(part.stockQuantity || 0), 0)

  const pieData = useMemo(() => {
    const map = {}
    parts.forEach((part) => {
      const key = part.categoryName || 'Uncategorized'
      map[key] = (map[key] || 0) + Number(part.stockQuantity || 0)
    })
    return Object.entries(map).map(([name, value]) => ({ name, value }))
  }, [parts])

  const recent = [...sales].sort((a, b) => new Date(b.saleDate) - new Date(a.saleDate)).slice(0, 5)
  const pendingOrders = analytics?.pendingOrdersCount ?? orders.filter((o) => o.status === 'PENDING').length

  return (
    <>
      <div className="stat-grid">
        {showCatalog ? (
          <>
            <Stat icon="car" color="#efe8ff" label="Vehicles" value={vehicles.length} hint="In catalog" />
            <Stat icon="link" color="#e8f1ff" label="Compatibility Mappings" value={mappings.length} hint="Part to vehicle" />
            <Stat icon="layers" color="#e7f6ff" label="Categories" value={categories.length} hint="Part categories" />
          </>
        ) : (
          <>
            {canSales && <Stat icon="dice" color="#efe8ff" label="Total Sales" value={money(totalSales)} hint={`${sales.length} invoices`} up />}
            {canPo && <Stat icon="wallet" color="#e8f1ff" label="Purchase Orders" value={money(totalPo)} hint={`${orders.length} orders`} />}
            {canParts && <Stat icon="send" color="#e7f6ff" label="Low Stock Items" value={analytics?.lowStockItemsCount ?? lowStock} hint="Needs reorder" down={lowStock > 0} />}
            {canParts && <Stat icon="home" color="#e9fbe8" label="Stock Items" value={stockItems} hint={`${parts.length} SKUs`} />}
          </>
        )}
      </div>

      {(showChart || showPie) && (
        <div className="dashboard-grid" style={showChart && showPie ? undefined : { gridTemplateColumns: '1fr' }}>
          {showChart && (
            <div className="panel">
              <div className="panel-head">
                <h2>Inventory Value</h2>
                <span className="chip">{money(inventoryValue)}</span>
              </div>
              <div style={{ height: 280 }}>
                <ResponsiveContainer width="100%" height="100%">
                  <BarChart data={[{ label: 'Stock on hand', value: inventoryValue }]}>
                    <CartesianGrid strokeDasharray="3 3" vertical={false} />
                    <XAxis dataKey="label" />
                    <YAxis />
                    <Tooltip formatter={(value) => money(value)} />
                    <Bar dataKey="value" fill="#a78bfa" radius={[8, 8, 0, 0]} />
                  </BarChart>
                </ResponsiveContainer>
              </div>
            </div>
          )}
          {showPie && (
            <div className="panel">
              <div className="panel-head"><h2>Stock by Category</h2></div>
              <div style={{ height: 240 }}>
                <ResponsiveContainer width="100%" height="100%">
                  <PieChart>
                    <Pie data={pieData.length ? pieData : [{ name: 'None', value: 1 }]} dataKey="value" nameKey="name" innerRadius={50} outerRadius={90}>
                      {(pieData.length ? pieData : [{ name: 'None', value: 1 }]).map((entry, index) => (
                        <Cell key={entry.name} fill={COLORS[index % COLORS.length]} />
                      ))}
                    </Pie>
                    <Tooltip />
                    <Legend />
                  </PieChart>
                </ResponsiveContainer>
              </div>
            </div>
          )}
        </div>
      )}

      {(showInvoices || showStock) && (
        <div className="bottom-grid" style={showInvoices && showStock ? undefined : { gridTemplateColumns: '1fr' }}>
          {showInvoices && (
            <div className="panel">
              <div className="panel-head">
                <h2>Recent Invoice</h2>
                <span className="chip">Sales Invoice</span>
              </div>
              <div className="table-wrap">
                <table>
                  <thead>
                    <tr>
                      <th>Invoice ID</th>
                      <th>Customer</th>
                      <th>Sales Date</th>
                      <th>Paid Amount</th>
                      <th>Sales Status</th>
                    </tr>
                  </thead>
                  <tbody>
                    {recent.length === 0 && <tr><td className="empty" colSpan={5}>No invoices yet</td></tr>}
                    {recent.map((sale) => (
                      <tr key={sale.id}>
                        <td>#{sale.invoiceNumber}</td>
                        <td>{sale.customerName}</td>
                        <td>{sale.saleDate ? new Date(sale.saleDate).toLocaleDateString() : '—'}</td>
                        <td>{money(sale.totalAmount)}</td>
                        <td>{statusBadge(sale.status)}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          )}
          {showStock && (
            <div className="panel">
              <div className="panel-head">
                <h2>Stock Overview</h2>
                <span className="chip">Live</span>
              </div>
              {canParts && (
                <>
                  <label>Active SKUs</label>
                  <strong style={{ fontSize: 36, display: 'block', margin: '8px 0' }}>{parts.length}</strong>
                  <div className={`delta ${lowStock > 0 ? 'down' : 'up'}`}>
                    {lowStock > 0 ? `${lowStock} below reorder level` : 'All items above reorder level'}
                  </div>
                </>
              )}
              {canPo && <div className="delta up" style={{ marginTop: 10 }}>Pending orders {pendingOrders}</div>}
            </div>
          )}
        </div>
      )}
    </>
  )
}

function Stat({ icon, color, label, value, hint, up, down }) {
  return (
    <div className="stat-card">
      <div className="stat-icon" style={{ background: color }}><Icon name={icon} /></div>
      <label>{label}</label>
      <strong>{value}</strong>
      <div className={`delta ${down ? 'down' : 'up'}`}>{hint}</div>
    </div>
  )
}
