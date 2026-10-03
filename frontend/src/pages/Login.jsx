import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../auth/AuthContext'
import { apiError } from '../api/client'

export default function Login() {
  const { login } = useAuth()
  const navigate = useNavigate()
  const [error, setError] = useState('')
  const [form, setForm] = useState({ username: '', password: '' })

  const onChange = (event) => setForm((prev) => ({ ...prev, [event.target.name]: event.target.value }))

  const submit = async (event) => {
    event.preventDefault()
    setError('')
    try {
      await login(form.username, form.password)
      navigate('/')
    } catch (err) {
      setError(apiError(err))
    }
  }

  return (
    <div className="login-shell">
      <form className="login-card" onSubmit={submit}>
        <div className="brand" style={{ padding: 0 }}>
          <strong style={{ color: '#15122a' }}>Lanka</strong>
          <span>Auto Parts</span>
        </div>
        <h1>Welcome back</h1>
        <p>Sign in to the Lanka Auto Parts operations dashboard.</p>
        {error && <div className="error">{error}</div>}
        <div className="field">
          <label>Username</label>
          <input name="username" value={form.username} onChange={onChange} required />
        </div>
        <div className="field">
          <label>Password</label>
          <input type="password" name="password" value={form.password} onChange={onChange} required minLength={6} />
        </div>
        <button className="primary" type="submit" style={{ width: '100%', padding: 12 }}>Sign in</button>
      </form>
    </div>
  )
}
