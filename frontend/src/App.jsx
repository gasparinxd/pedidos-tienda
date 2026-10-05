import { useEffect, useState } from 'react'
import './App.css'

function App() {
  const [pedidos, setPedidos] = useState([])
  const [error, setError] = useState(null)

  useEffect(() => {
    fetch('/api/pedidos')
      .then((res) => {
        if (!res.ok) throw new Error(`HTTP ${res.status}`)
        return res.json()
      })
      .then(setPedidos)
      .catch((err) => setError(err.message))
  }, [])

  return (
    <main>
      <h1>Pedidos Tienda</h1>
      {error && <p className="error">No se pudo conectar con el backend: {error}</p>}
      {!error && pedidos.length === 0 && <p>No hay pedidos registrados.</p>}
      <ul>
        {pedidos.map((p) => (
          <li key={p.id}>
            #{p.id} — {p.cliente} — {p.total} — {p.estado}
          </li>
        ))}
      </ul>
    </main>
  )
}

export default App
