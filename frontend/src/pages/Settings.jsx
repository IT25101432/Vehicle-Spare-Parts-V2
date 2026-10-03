import { useState } from 'react'
import api, { apiError } from '../api/client'
import { useAuth } from '../auth/AuthContext'
import { roleLabel } from '../auth/roles'

export default function Settings() {
  const { user } = useAuth()
  const [form, setForm] = useState({ currentPassword: '', newPassword: '', confirmPassword: '' })
  const [message, setMessage] = useState('')
  const [error, setError] = useState('')

  const submit = async (e) => {
    e.preventDefault()
    setError('')
    setMessage('')
    if (form.newPassword !== form.confirmPassword) {
      setError('New passwords do not match')
      return
    }
    try {
      await api.post('/auth/change-password', {
        oldPassword: form.currentPassword,
        newPassword: form.newPassword,
      })
      setMessage('Password updated')
      setForm({ currentPassword: '', newPassword: '', confirmPassword: '' })
    } catch (err) {
      setError(apiError(err))
    }
  }

  return (
    <div className="page-card" style={{ maxWidth: 560 }}>
      <h2 style={{ marginTop: 0 }}>Account</h2>
      <p>{user.firstName} {user.lastName} · {user.email}</p>
      <p>Role: {roleLabel(user.role)}</p>
      {error && <div className="error">{error}</div>}
      {message && <div className="badge green">{message}</div>}
      <form onSubmit={submit} style={{ marginTop: 16 }}>
        <div className="field"><label>Current password</label><input type="password" value={form.currentPassword} onChange={(e) => setForm((p) => ({ ...p, currentPassword: e.target.value }))} required /></div>
        <div className="field"><label>New password</label><input type="password" minLength={6} value={form.newPassword} onChange={(e) => setForm((p) => ({ ...p, newPassword: e.target.value }))} required /></div>
        <div className="field"><label>Confirm password</label><input type="password" value={form.confirmPassword} onChange={(e) => setForm((p) => ({ ...p, confirmPassword: e.target.value }))} required /></div>
        <button className="primary" type="submit">Update password</button>
      </form>
    </div>
  )
}
