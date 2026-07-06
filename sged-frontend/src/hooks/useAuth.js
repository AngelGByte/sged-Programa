import { useContext } from 'react'
import { AuthContext } from '../context/AuthContext'

/**
 * Hook personalizado useAuth.
 * Acceso simplificado al contexto de autenticación.
 */
export const useAuth = () => {
  const context = useContext(AuthContext)
  
  if (!context) {
    throw new Error('useAuth debe ser usado dentro de AuthProvider')
  }
  
  return context
}
