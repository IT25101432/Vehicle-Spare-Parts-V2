import { useCallback, useEffect, useState } from 'react'
import api, { apiError } from '../api/client'
import DataTable, { money, statusBadge } from '../components/DataTable'
import Modal from '../components/Modal'
import { useResource } from '../hooks/useResource'

const empty = { partNumber: '', name: '', description: '', price: '', stockQuantity: '', reorderLevel: '', categoryId: '' }

export default function Inventory() {
  const loader = useCallback(async () => (await api.get('/parts')).data, [])
  const { rows, error, setError, reload } = useResource(loader)
  const [categories, setCategories] = useState([])
  const [open, setOpen] = useState(false)
  const [editing, setEditing] = useState(null)
  const [form, setForm] = useState(empty)

  useEffect(() => { api.get('/categories').then((res) => setCategories(res.data || [])).catch(() => {}) }, [])
  const change = (e) => setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }))

  const save = async (e) => {
    e.preventDefault()
    const payload = {
      ...form,
      price: Number(form.price),
      stockQuantity: Number(form.stockQuantity),
      reorderLevel: Number(form.reorderLevel),
      categoryId: form.categoryId ? Number(form.categoryId) : null,
    }
    try {
      if (editing) await api.put(`/parts/${editing.id}`, payload)
      else await api.post('/parts', payload)
      setOpen(false)
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
        extraActions={<button className="primary" type="button" onClick={() => { setEditing(null); setForm(empty); setOpen(true) }}>Add part</button>}
        columns={[
          { key: 'partNumber', label: 'Part #' },
          { key: 'name', label: 'Name' },
          { key: 'categoryName', label: 'Category' },
          { key: 'price', label: 'Price', render: (row) => money(row.price) },
          { key: 'stockQuantity', label: 'Stock' },
          { key: 'reorderLevel', label: 'Reorder' },
          { key: 'status', label: 'Status', render: (row) => statusBadge(Number(row.stockQuantity) <= Number(row.reorderLevel || 0) ? 'Low stock' : 'In stock') },
        ]}
        onEdit={(row) => { setEditing(row); setForm({ ...empty, ...row, categoryId: row.categoryId || '' }); setOpen(true) }}
        onDelete={async (row) => { if (confirm('Delete this part?')) { await api.delete(`/parts/${row.id}`); reload() } }}
      />
      {open && (
        <Modal title={editing ? 'Edit part' : 'New part'} onClose={() => setOpen(false)}>
          <form onSubmit={save}>
            <div className="form-grid">
              <div className="field"><label>Part number</label><input name="partNumber" value={form.partNumber} onChange={change} required /></div>
              <div className="field"><label>Name</label><input name="name" value={form.name} onChange={change} required /></div>
              <div className="field full"><label>Description</label><textarea name="description" value={form.description || ''} onChange={change} /></div>
              <div className="field"><label>Price</label><input type="number" step="0.01" name="price" value={form.price} onChange={change} required /></div>
              <div className="field"><label>Stock</label><input type="number" name="stockQuantity" value={form.stockQuantity} onChange={change} required /></div>
              <div className="field"><label>Reorder level</label><input type="number" name="reorderLevel" value={form.reorderLevel} onChange={change} required /></div>
              <div className="field"><label>Category</label>
                <select name="categoryId" value={form.categoryId} onChange={change}>
                  <option value="">Select</option>
                  {categories.map((c) => <option key={c.id} value={c.id}>{c.name}</option>)}
                </select>
              </div>
            </div>
            <div className="modal-actions">
              <button className="secondary" type="button" onClick={() => setOpen(false)}>Cancel</button>
              <button className="primary" type="submit">Save</button>
            </div>
          </form>
        </Modal>
      )}
    </>
  )
}
