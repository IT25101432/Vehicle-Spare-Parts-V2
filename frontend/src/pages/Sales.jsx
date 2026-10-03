import { useCallback, useEffect, useState } from 'react'
import api, { apiError } from '../api/client'
import DataTable, { money, statusBadge } from '../components/DataTable'
import Modal from '../components/Modal'
import { useResource } from '../hooks/useResource'

const emptyItem = { partNumber: '', quantity: 1, unitPrice: '' }

export default function Sales() {
  const loader = useCallback(async () => (await api.get('/sales')).data, [])
  const { rows, error, setError, reload } = useResource(loader)
  const [parts, setParts] = useState([])
  const [open, setOpen] = useState(false)
  const [statusOpen, setStatusOpen] = useState(null)
  const [form, setForm] = useState({ customerName: '', customerPhone: '', items: [{ ...emptyItem }] })
  const [statusForm, setStatusForm] = useState({ status: 'COMPLETED', remarks: '' })

  useEffect(() => { api.get('/parts').then((res) => setParts(res.data || [])).catch(() => {}) }, [])

  const save = async (e) => {
    e.preventDefault()
    try {
      await api.post('/sales', {
        customerName: form.customerName,
        customerPhone: form.customerPhone,
        items: form.items.map((item) => ({
          partNumber: item.partNumber,
          quantity: Number(item.quantity),
          unitPrice: Number(item.unitPrice),
        })),
      })
      setOpen(false)
      reload()
    } catch (err) {
      setError(apiError(err))
    }
  }

  const updateStatus = async (e) => {
    e.preventDefault()
    try {
      await api.put(`/sales/${statusOpen.id}/status`, statusForm)
      setStatusOpen(null)
      reload()
    } catch (err) {
      setError(apiError(err))
    }
  }

  return (
    <>
      {error && <div className="error">{error}</div>}
      <DataTable
        rows={rows}
        extraActions={<button className="primary" type="button" onClick={() => { setForm({ customerName: '', customerPhone: '', items: [{ ...emptyItem }] }); setOpen(true) }}>New sale</button>}
        columns={[
          { key: 'invoiceNumber', label: 'Invoice' },
          { key: 'customerName', label: 'Customer' },
          { key: 'customerPhone', label: 'Phone' },
          { key: 'saleDate', label: 'Date', render: (row) => row.saleDate ? new Date(row.saleDate).toLocaleDateString() : '—' },
          { key: 'totalAmount', label: 'Amount', render: (row) => money(row.totalAmount) },
          { key: 'status', label: 'Status', render: (row) => statusBadge(row.status) },
        ]}
        onEdit={(row) => { setStatusOpen(row); setStatusForm({ status: row.status || 'COMPLETED', remarks: '' }) }}
        onDelete={async (row) => { if (confirm('Delete this sale?')) { await api.delete(`/sales/${row.id}`); reload() } }}
      />
      {open && (
        <Modal title="New sale" onClose={() => setOpen(false)}>
          <form onSubmit={save}>
            <div className="form-grid">
              <div className="field"><label>Customer</label><input value={form.customerName} onChange={(e) => setForm((p) => ({ ...p, customerName: e.target.value }))} required /></div>
              <div className="field"><label>Phone</label><input value={form.customerPhone} onChange={(e) => setForm((p) => ({ ...p, customerPhone: e.target.value }))} /></div>
            </div>
            {form.items.map((item, index) => (
              <div className="item-row" key={index}>
                <select value={item.partNumber} onChange={(e) => {
                  const part = parts.find((p) => p.partNumber === e.target.value)
                  const next = [...form.items]
                  next[index] = { ...item, partNumber: e.target.value, unitPrice: part?.price ?? item.unitPrice }
                  setForm((p) => ({ ...p, items: next }))
                }} required>
                  <option value="">Part</option>
                  {parts.map((p) => <option key={p.id} value={p.partNumber}>{p.partNumber} — {p.name}</option>)}
                </select>
                <input type="number" min="1" value={item.quantity} onChange={(e) => {
                  const next = [...form.items]; next[index] = { ...item, quantity: e.target.value }; setForm((p) => ({ ...p, items: next }))
                }} />
                <input type="number" step="0.01" value={item.unitPrice} onChange={(e) => {
                  const next = [...form.items]; next[index] = { ...item, unitPrice: e.target.value }; setForm((p) => ({ ...p, items: next }))
                }} />
                <button className="danger" type="button" onClick={() => setForm((p) => ({ ...p, items: p.items.filter((_, i) => i !== index) }))}>x</button>
              </div>
            ))}
            <button className="secondary" type="button" onClick={() => setForm((p) => ({ ...p, items: [...p.items, { ...emptyItem }] }))}>Add line</button>
            <div className="modal-actions">
              <button className="secondary" type="button" onClick={() => setOpen(false)}>Cancel</button>
              <button className="primary" type="submit">Create</button>
            </div>
          </form>
        </Modal>
      )}
      {statusOpen && (
        <Modal title={`Update ${statusOpen.invoiceNumber}`} onClose={() => setStatusOpen(null)}>
          <form onSubmit={updateStatus}>
            <div className="field">
              <label>Status</label>
              <select value={statusForm.status} onChange={(e) => setStatusForm((p) => ({ ...p, status: e.target.value }))}>
                {['PENDING', 'COMPLETED', 'VOIDED'].map((s) => <option key={s}>{s}</option>)}
              </select>
            </div>
            <div className="field">
              <label>Remarks</label>
              <textarea value={statusForm.remarks} onChange={(e) => setStatusForm((p) => ({ ...p, remarks: e.target.value }))} />
            </div>
            <div className="modal-actions">
              <button className="secondary" type="button" onClick={() => setStatusOpen(null)}>Cancel</button>
              <button className="primary" type="submit">Update</button>
            </div>
          </form>
        </Modal>
      )}
    </>
  )
}
