import { useCallback, useEffect, useState } from 'react'
import { apiError } from '../api/client'

export function useResource(loader) {
  const [rows, setRows] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  const reload = useCallback(async () => {
    setLoading(true)
    setError('')
    try {
      const data = await loader()
      setRows(Array.isArray(data) ? data : [])
    } catch (err) {
      setError(apiError(err))
    } finally {
      setLoading(false)
    }
  }, [loader])

  useEffect(() => { reload() }, [reload])
  return { rows, loading, error, setError, reload, setRows }
}
