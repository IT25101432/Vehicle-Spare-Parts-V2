import { useMemo, useState } from 'react'
import { Icon } from './Icons'

export default function DataTable({ columns, rows, onEdit, onDelete, extraActions, searchPlaceholder = 'Search' }) {
  const [query, setQuery] = useState('')
  const filtered = useMemo(() => {
    const q = query.trim().toLowerCase()
    if (!q) return rows
    return rows.filter((row) => JSON.stringify(row).toLowerCase().includes(q))
  }, [rows, query])

  return (
    <div className="page-card">
      <div className="toolbar">
        <input className="search" value={query} onChange={(e) => setQuery(e.target.value)} placeholder={searchPlaceholder} />
        <div>{extraActions}</div>
      </div>
      <div className="table-wrap">
        <table>
          <thead>
            <tr>
              {columns.map((col) => <th key={col.key}>{col.label}</th>)}
              {(onEdit || onDelete) && <th>Actions</th>}
            </tr>
          </thead>
          <tbody>
            {filtered.length === 0 && (
              <tr><td className="empty" colSpan={columns.length + 1}>No records found</td></tr>
            )}
            {filtered.map((row) => (
              <tr key={row.id || JSON.stringify(row)}>
                {columns.map((col) => (
                  <td key={col.key}>{col.render ? col.render(row) : row[col.key]}</td>
                ))}
                {(onEdit || onDelete) && (
                  <td>
                    <div className="row-actions">
                      {onEdit && <button className="icon-action" type="button" onClick={() => onEdit(row)}><Icon name="edit" /></button>}
                      {onDelete && <button className="icon-action" type="button" onClick={() => onDelete(row)}><Icon name="trash" /></button>}
                    </div>
                  </td>
                )}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  )
}

export function statusBadge(status) {
  const value = String(status || '').toUpperCase()
  const tone = value.includes('COMPLETE') || value.includes('DELIVER') || value.includes('RECEIVED') || value === 'APPROVED'
    ? 'green'
    : value.includes('PROGRESS') || value === 'PENDING'
      ? 'blue'
      : value.includes('CANCEL') || value === 'VOIDED'
        ? 'red'
        : 'amber'
  return <span className={`badge ${tone}`}>{status || '—'}</span>
}

export function money(value) {
  const n = Number(value || 0)
  // Fixed "Rs." prefix so output is identical regardless of the browser's locale.
  return `Rs. ${n.toLocaleString('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}`
}
