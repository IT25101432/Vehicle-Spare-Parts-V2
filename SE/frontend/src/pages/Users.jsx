import { useCallback, useState } from 'react'
import api, { apiError } from '../api/client'
import DataTable, { statusBadge } from '../components/DataTable'
import Modal from '../components/Modal'
import { useResource } from '../hooks/useResource'
import { ROLE_OPTIONS, roleLabel } from '../auth/roles'

const empty = { username: '', password: '', email: '', firstName: '', lastName: '', role: 'ROLE_SALES_STAFF' }

export default function Users() {
  const loader = useCallback(async () => (await api.get('/users')).data, [])
  const { rows, error, setError, reload } = useResource(loader)
  const [open, setOpen] = useState(false)
  const [editing, setEditing] = useState(null)
  const [form, setForm] = useState(empty)

  const change = (e) => setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }))

  const save = async (e) => {
    e.preventDefault()
    try {
      if (editing) {
        await api.put(`/users/${editing.id}`, {
          email: form.email,
          firstName: form.firstName,
          lastName: form.lastName,
          role: form.role,
          password: form.password || undefined,
        })
      } else {
        await api.post('/users', form)
      }
      setOpen(false)
      setEditing(null)
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
        extraActions={<button className="primary" type="button" onClick={() => { setForm(empty); setEditing(null); setOpen(true) }}>Add staff</button>}
        columns={[
          { key: 'username', label: 'Username' },
          { key: 'name', label: 'Name', render: (row) => `${row.firstName || ''} ${row.lastName || ''}`.trim() },
          { key: 'email', label: 'Email' },
          { key: 'role', label: 'Role', render: (row) => roleLabel(row.role) },
          { key: 'enabled', label: 'Status', render: (row) => statusBadge(row.enabled ? 'Active' : 'Disabled') },
        ]}
        onEdit={(row) => { setEditing(row); setForm({ ...empty, ...row, password: '' }); setOpen(true) }}
        onDelete={async (row) => { if (confirm('Delete this user?')) { await api.delete(`/users/${row.id}`); reload() } }}
      />
      {open && (
        <Modal title={editing ? 'Edit staff' : 'New staff'} onClose={() => setOpen(false)}>
          <form onSubmit={save}>
            <div className="form-grid">
              {!editing && <div className="field"><label>Username</label><input name="username" value={form.username} onChange={change} required /></div>}
              <div className="field"><label>Email</label><input type="email" name="email" value={form.email} onChange={change} required /></div>
              <div className="field"><label>First name</label><input name="firstName" value={form.firstName} onChange={change} required /></div>
              <div className="field"><label>Last name</label><input name="lastName" value={form.lastName} onChange={change} required /></div>
              <div className="field"><label>Role</label>
                <select name="role" value={form.role} onChange={change}>
                  {ROLE_OPTIONS.map((role) => <option key={role.value} value={role.value}>{role.label}</option>)}
                </select>
              </div>
              <div className="field"><label>{editing ? 'New password (optional)' : 'Password'}</label><input type="password" name="password" value={form.password} onChange={change} minLength={editing ? 0 : 6} required={!editing} /></div>
            </div>
            {editing && (
              <button className="secondary" type="button" style={{ marginTop: 8 }} onClick={async () => { await api.patch(`/users/${editing.id}/toggle-status`); reload(); setOpen(false) }}>
                Toggle status
              </button>
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
