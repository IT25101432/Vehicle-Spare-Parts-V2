import { useCallback, useState } from 'react'
import api, { apiError } from '../api/client'
import DataTable, { money } from '../components/DataTable'
import Modal from '../components/Modal'
import { useResource } from '../hooks/useResource'

const empty = { name: '', reportType: 'SALES_SUMMARY', startDate: '', endDate: '' }

export default function Reports() {
  const loader = useCallback(async () => (await api.get('/reports')).data, [])
  const { rows, error, setError, reload } = useResource(loader)
  const [open, setOpen] = useState(false)
  const [editing, setEditing] = useState(null)
  const [form, setForm] = useState(empty)
  const change = (e) => setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }))

  const save = async (e) => {
    e.preventDefault()
    try {
      if (editing) await api.put(`/reports/${editing.id}`, { name: form.name, endDate: form.endDate })
      else await api.post('/reports', form)
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
        extraActions={<button className="primary" type="button" onClick={() => { setEditing(null); setForm(empty); setOpen(true) }}>New report</button>}
        columns={[
          { key: 'reportId', label: 'Report ID' },
          { key: 'name', label: 'Name' },
          { key: 'reportType', label: 'Type' },
          { key: 'startDate', label: 'Start' },
          { key: 'endDate', label: 'End' },
          { key: 'totalFinancialValue', label: 'Value', render: (row) => money(row.totalFinancialValue) },
        ]}
        onEdit={(row) => { setEditing(row); setForm({ name: row.name, reportType: row.reportType, startDate: row.startDate || '', endDate: row.endDate || '' }); setOpen(true) }}
        onDelete={async (row) => { if (confirm('Delete this report?')) { await api.delete(`/reports/${row.id}`); reload() } }}
      />
      {open && (
        <Modal title={editing ? 'Update report' : 'New report'} onClose={() => setOpen(false)}>
          <form onSubmit={save}>
            <div className="field"><label>Name</label><input name="name" value={form.name} onChange={change} required /></div>
            {!editing && (
              <>
                <div className="field">
                  <label>Type</label>
                  <select name="reportType" value={form.reportType} onChange={change}>
                    <option>SALES_SUMMARY</option>
                    <option>INVENTORY_SUMMARY</option>
                    <option>PROCUREMENT_SUMMARY</option>
                  </select>
                </div>
                <div className="form-grid">
                  <div className="field"><label>Start date</label><input type="date" name="startDate" value={form.startDate} onChange={change} required /></div>
                  <div className="field"><label>End date</label><input type="date" name="endDate" value={form.endDate} onChange={change} required /></div>
                </div>
              </>
            )}
            {editing && <div className="field"><label>End date</label><input type="date" name="endDate" value={form.endDate} onChange={change} /></div>}
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
