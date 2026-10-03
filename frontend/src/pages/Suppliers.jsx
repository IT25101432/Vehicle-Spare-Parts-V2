import { useCallback, useState } from 'react'
import api, { apiError } from '../api/client'
import DataTable, { statusBadge } from '../components/DataTable'
import Modal from '../components/Modal'
import { useResource } from '../hooks/useResource'

const empty = { name: '', contactPerson: '', email: '', phone: '', address: '' }

export default function Suppliers() {
  const loader = useCallback(async () => (await api.get('/suppliers')).data, [])
  const { rows, error, setError, reload } = useResource(loader)
  const [open, setOpen] = useState(false)
  const [editing, setEditing] = useState(null)
  const [form, setForm] = useState(empty)
  const change = (e) => setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }))

  const save = async (e) => {
    e.preventDefault()
    try {
      if (editing) await api.put(`/suppliers/${editing.id}`, form)
      else await api.post('/suppliers', form)
      setOpen(false)
      reload()
    } catch (err) {
      setError(apiError(err))
    }
  }

  const isActive = (row) => row.isActive ?? row.active

  return (
    <>
      {error && <div className="error">{error}</div>}
      <DataTable
        rows={rows}
        extraActions={<button className="primary" type="button" onClick={() => { setEditing(null); setForm(empty); setOpen(true) }}>Add supplier</button>}
        columns={[
          { key: 'name', label: 'Name' },
          { key: 'contactPerson', label: 'Contact' },
          { key: 'email', label: 'Email' },
          { key: 'phone', label: 'Phone' },
          { key: 'address', label: 'Address' },
          { key: 'active', label: 'Status', render: (row) => statusBadge(isActive(row) ? 'Active' : 'Inactive') },
        ]}
        onEdit={(row) => { setEditing(row); setForm({ name: row.name, contactPerson: row.contactPerson || '', email: row.email || '', phone: row.phone || '', address: row.address || '' }); setOpen(true) }}
        onDelete={async (row) => { if (confirm('Delete this supplier?')) { await api.delete(`/suppliers/${row.id}`); reload() } }}
      />
      {open && (
        <Modal title={editing ? 'Edit supplier' : 'New supplier'} onClose={() => setOpen(false)}>
          <form onSubmit={save}>
            <div className="form-grid">
              <div className="field"><label>Name</label><input name="name" value={form.name} onChange={change} required /></div>
              <div className="field"><label>Contact person</label><input name="contactPerson" value={form.contactPerson} onChange={change} /></div>
              <div className="field"><label>Email</label><input type="email" name="email" value={form.email} onChange={change} /></div>
              <div className="field"><label>Phone</label><input name="phone" value={form.phone} onChange={change} /></div>
              <div className="field full"><label>Address</label><textarea name="address" value={form.address} onChange={change} /></div>
            </div>
            {editing && (
              <button className="secondary" type="button" onClick={async () => { await api.patch(`/suppliers/${editing.id}/toggle-status`); reload(); setOpen(false) }}>Toggle status</button>
            )}
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
