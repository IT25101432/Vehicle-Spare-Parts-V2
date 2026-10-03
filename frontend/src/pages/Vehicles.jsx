import { useCallback, useState } from 'react'
import api, { apiError } from '../api/client'
import DataTable from '../components/DataTable'
import Modal from '../components/Modal'
import { useResource } from '../hooks/useResource'

const empty = { make: '', model: '', year: '' }

export default function Vehicles() {
  const loader = useCallback(async () => (await api.get('/vehicles')).data, [])
  const { rows, error, setError, reload } = useResource(loader)
  const [open, setOpen] = useState(false)
  const [editing, setEditing] = useState(null)
  const [form, setForm] = useState(empty)
  const change = (e) => setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }))

  const save = async (e) => {
    e.preventDefault()
    const payload = { ...form, year: Number(form.year) }
    try {
      if (editing) await api.put(`/vehicles/${editing.id}`, payload)
      else await api.post('/vehicles', payload)
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
        extraActions={<button className="primary" type="button" onClick={() => { setEditing(null); setForm(empty); setOpen(true) }}>Add vehicle</button>}
        columns={[
          { key: 'make', label: 'Make' },
          { key: 'model', label: 'Model' },
          { key: 'year', label: 'Year' },
        ]}
        onEdit={(row) => { setEditing(row); setForm({ make: row.make, model: row.model, year: row.year }); setOpen(true) }}
        onDelete={async (row) => { if (confirm('Delete this vehicle?')) { await api.delete(`/vehicles/${row.id}`); reload() } }}
      />
      {open && (
        <Modal title={editing ? 'Edit vehicle' : 'New vehicle'} onClose={() => setOpen(false)}>
          <form onSubmit={save}>
            <div className="form-grid">
              <div className="field"><label>Make</label><input name="make" value={form.make} onChange={change} required /></div>
              <div className="field"><label>Model</label><input name="model" value={form.model} onChange={change} required /></div>
              <div className="field"><label>Year</label><input type="number" name="year" value={form.year} onChange={change} required /></div>
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
