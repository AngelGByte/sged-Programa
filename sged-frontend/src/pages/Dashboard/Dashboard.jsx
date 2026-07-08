import { useAuth } from '../../hooks/useAuth'
import './Dashboard.scss'

/**
 * Dashboard - Página principal después de login.
 */
function Dashboard() {
  const { user } = useAuth()

  return (
    <div className="dashboard">
      <header className="dashboard-header">
        <h1>Bienvenido, {user?.nombre || 'Usuario'}!</h1>
        <p>Esta es tu página de inicio.</p>
      </header>
      
      <div className="dashboard-grid">
        <div className="dashboard-card">
          <h2>Cursos</h2>
          <p>Gestiona tus cursos</p>
        </div>
        <div className="dashboard-card">
          <h2>Calificaciones</h2>
          <p>Revisa tus calificaciones</p>
        </div>
        <div className="dashboard-card">
          <h2>Asistencias</h2>
          <p>Registra asistencias</p>
        </div>
        <div className="dashboard-card">
          <h2>Reportes</h2>
          <p>Visualiza reportes</p>
        </div>
      </div>
    </div>
  )
}

export default Dashboard
