import { useCallback, useEffect, useState } from 'react'
import api, { apiError } from '../api/client'
import DataTable from '../components/DataTable'
import Modal from '../components/Modal'
import { useResource } from '../hooks/useResource'

export default function Compatibility() {
  const loader = useCallback(async () => (await api.get('/mappings')).data, [])
  const { rows, error, setError, reload } = useResource(loader)
  const [vehicles, setVehicles] = useState([])
  const [parts, setParts] = useState([])
  const [open, setOpen] = useState(false)
  const [form, setForm] = useState({ partNumber: '', vehicleId: '' })

  useEffect(() => {
    api.get('/vehicles').then((res) => setVehicles(res.data || [])).catch(() => {})
    api.get('/parts').then((res) => setParts(res.data || [])).catch(() => {})
  }, [])

  const save = async (e) => {
    e.preventDefault()
    try {
      await api.post('/mappings', { partNumber: form.partNumber, vehicleId: Number(form.vehicleId) })
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
        extraActions={<button className="primary" type="button" onClick={() => { setForm({ partNumber: '', vehicleId: '' }); setOpen(true) }}>Add mapping</button>}
        columns={[
          { key: 'partNumber', label: 'Part #' },
          { key: 'vehicle', label: 'Vehicle', render: (row) => row.vehicle ? `${row.vehicle.make} ${row.vehicle.model} (${row.vehicle.year})` : '—' },
        ]}
        onDelete={async (row) => { if (confirm('Delete this mapping?')) { await api.delete(`/mappings/${row.id}`); reload() } }}
      />
      {open && (
        <Modal title="New compatibility mapping" onClose={() => setOpen(false)}>
          <form onSubmit={save}>
            <div className="field">
              <label>Part number</label>
              {parts.length > 0 ? (
                <select value={form.partNumber} onChange={(e) => setForm((p) => ({ ...p, partNumber: e.target.value }))} required>
                  <option value="">Select part</option>
                  {parts.map((part) => <option key={part.id} value={part.partNumber}>{part.partNumber} — {part.name}</option>)}
                </select>
              ) : (
                <input value={form.partNumber} onChange={(e) => setForm((p) => ({ ...p, partNumber: e.target.value }))} required />
              )}
            </div>
            <div className="field">
              <label>Vehicle</label>
              <select value={form.vehicleId} onChange={(e) => setForm((p) => ({ ...p, vehicleId: e.target.value }))} required>
                <option value="">Select vehicle</option>
                {vehicles.map((v) => <option key={v.id} value={v.id}>{v.make} {v.model} {v.year}</option>)}
              </select>
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
