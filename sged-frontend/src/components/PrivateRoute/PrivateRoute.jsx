import { Navigate } from 'react-router-dom'
import { useAuth } from '../../hooks/useAuth'

/**
 * Componente PrivateRoute - Protege rutas que requieren autenticación.
 */
const PrivateRoute = ({ children, requiredRole }) => {
  const { isAuthenticated, loading, userRole } = useAuth()

  if (loading) {
    return <div>Cargando...</div>
  }

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />
  }

  if (requiredRole && userRole !== requiredRole) {
    return <Navigate to="/dashboard" replace />
  }

  return children
}

export default PrivateRoute
