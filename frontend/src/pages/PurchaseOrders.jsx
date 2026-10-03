import { useCallback, useEffect, useState } from 'react'
import api, { apiError } from '../api/client'
import DataTable, { money, statusBadge } from '../components/DataTable'
import Modal from '../components/Modal'
import { useResource } from '../hooks/useResource'

const emptyItem = { sparePartId: '', quantity: 1, unitPrice: '' }

export default function PurchaseOrders() {
  const loader = useCallback(async () => (await api.get('/po')).data, [])
  const { rows, error, setError, reload } = useResource(loader)
  const [suppliers, setSuppliers] = useState([])
  const [parts, setParts] = useState([])
  const [open, setOpen] = useState(false)
  const [statusOpen, setStatusOpen] = useState(null)
  const [form, setForm] = useState({ supplierId: '', expectedDeliveryDate: '', items: [{ ...emptyItem }] })
  const [statusForm, setStatusForm] = useState({ status: 'APPROVED', expectedDeliveryDate: '' })

  useEffect(() => {
    api.get('/suppliers').then((res) => setSuppliers(res.data || [])).catch(() => {})
    api.get('/parts').then((res) => setParts(res.data || [])).catch(() => {})
  }, [])

  const save = async (e) => {
    e.preventDefault()
    try {
      await api.post('/po', {
        supplierId: Number(form.supplierId),
        expectedDeliveryDate: form.expectedDeliveryDate,
        items: form.items.map((item) => ({
          sparePartId: Number(item.sparePartId),
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
      await api.put(`/po/${statusOpen.id}`, statusForm)
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
        extraActions={<button className="primary" type="button" onClick={() => { setForm({ supplierId: '', expectedDeliveryDate: '', items: [{ ...emptyItem }] }); setOpen(true) }}>New PO</button>}
        columns={[
          { key: 'orderNumber', label: 'PO #' },
          { key: 'supplierName', label: 'Supplier' },
          { key: 'orderDate', label: 'Order date' },
          { key: 'expectedDeliveryDate', label: 'Expected' },
          { key: 'totalAmount', label: 'Total', render: (row) => money(row.totalAmount) },
          { key: 'status', label: 'Status', render: (row) => statusBadge(row.status) },
        ]}
        onEdit={(row) => { setStatusOpen(row); setStatusForm({ status: row.status || 'APPROVED', expectedDeliveryDate: row.expectedDeliveryDate || '' }) }}
        onDelete={async (row) => { if (confirm('Delete this purchase order?')) { await api.delete(`/po/${row.id}`); reload() } }}
      />
      {open && (
        <Modal title="New purchase order" onClose={() => setOpen(false)}>
          <form onSubmit={save}>
            <div className="form-grid">
              <div className="field">
                <label>Supplier</label>
                <select value={form.supplierId} onChange={(e) => setForm((p) => ({ ...p, supplierId: e.target.value }))} required>
                  <option value="">Select</option>
                  {suppliers.map((s) => <option key={s.id} value={s.id}>{s.name}</option>)}
                </select>
              </div>
              <div className="field">
                <label>Expected delivery</label>
                <input type="date" value={form.expectedDeliveryDate} onChange={(e) => setForm((p) => ({ ...p, expectedDeliveryDate: e.target.value }))} required />
              </div>
            </div>
            <label style={{ fontWeight: 600, fontSize: 13 }}>Line items</label>
            {form.items.map((item, index) => (
              <div className="item-row" key={index}>
                <select value={item.sparePartId} onChange={(e) => {
                  const next = [...form.items]
                  const part = parts.find((p) => String(p.id) === e.target.value)
                  next[index] = { ...item, sparePartId: e.target.value, unitPrice: part?.price ?? item.unitPrice }
                  setForm((p) => ({ ...p, items: next }))
                }} required>
                  <option value="">Part</option>
                  {parts.map((p) => <option key={p.id} value={p.id}>{p.partNumber} — {p.name}</option>)}
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
        <Modal title={`Update ${statusOpen.orderNumber}`} onClose={() => setStatusOpen(null)}>
          <form onSubmit={updateStatus}>
            <div className="field">
              <label>Status</label>
              <select value={statusForm.status} onChange={(e) => setStatusForm((p) => ({ ...p, status: e.target.value }))}>
                {['PENDING', 'APPROVED', 'RECEIVED', 'CANCELLED'].map((s) => <option key={s}>{s}</option>)}
              </select>
            </div>
            <div className="field">
              <label>Expected delivery</label>
              <input type="date" value={statusForm.expectedDeliveryDate} onChange={(e) => setStatusForm((p) => ({ ...p, expectedDeliveryDate: e.target.value }))} />
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
