import { useCallback, useState } from 'react'
import api, { apiError } from '../api/client'
import DataTable from '../components/DataTable'
import Modal from '../components/Modal'
import { useResource } from '../hooks/useResource'

const empty = { name: '', description: '' }

export default function Categories() {
  const loader = useCallback(async () => (await api.get('/categories')).data, [])
  const { rows, error, setError, reload } = useResource(loader)
  const [open, setOpen] = useState(false)
  const [editing, setEditing] = useState(null)
  const [form, setForm] = useState(empty)
  const change = (e) => setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }))

  const save = async (e) => {
    e.preventDefault()
    try {
      if (editing) await api.put(`/categories/${editing.id}`, form)
      else await api.post('/categories', form)
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
        extraActions={<button className="primary" type="button" onClick={() => { setEditing(null); setForm(empty); setOpen(true) }}>Add category</button>}
        columns={[
          { key: 'name', label: 'Name' },
          { key: 'description', label: 'Description' },
        ]}
        onEdit={(row) => { setEditing(row); setForm({ name: row.name, description: row.description || '' }); setOpen(true) }}
        onDelete={async (row) => { if (confirm('Delete this category?')) { await api.delete(`/categories/${row.id}`); reload() } }}
      />
      {open && (
        <Modal title={editing ? 'Edit category' : 'New category'} onClose={() => setOpen(false)}>
          <form onSubmit={save}>
            <div className="field"><label>Name</label><input name="name" value={form.name} onChange={change} required /></div>
            <div className="field"><label>Description</label><textarea name="description" value={form.description} onChange={change} /></div>
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
