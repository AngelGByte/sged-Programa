import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom'
import { AuthProvider } from './context/AuthContext'
import { ToastContainer } from 'react-toastify'
import PrivateRoute from './components/PrivateRoute/PrivateRoute'

// Páginas
import Login from './pages/Login/Login'
import Register from './pages/Register/Register'
import Dashboard from './pages/Dashboard/Dashboard'
import Cursos from './pages/Cursos/Cursos'
import Calificaciones from './pages/Calificaciones/Calificaciones'
import Asistencias from './pages/Asistencias/Asistencias'
import AdminPanel from './pages/AdminPanel/AdminPanel'

import 'react-toastify/dist/ReactToastify.css'

function App() {
  return (
    <Router>
      <AuthProvider>
        <Routes>
          {/* Rutas públicas */}
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />

          {/* Rutas protegidas */}
          <Route
            path="/dashboard"
            element={<PrivateRoute><Dashboard /></PrivateRoute>}
          />
          <Route
            path="/cursos"
            element={<PrivateRoute><Cursos /></PrivateRoute>}
          />
          <Route
            path="/calificaciones"
            element={<PrivateRoute><Calificaciones /></PrivateRoute>}
          />
          <Route
            path="/asistencias"
            element={<PrivateRoute><Asistencias /></PrivateRoute>}
          />
          <Route
            path="/admin"
            element={<PrivateRoute><AdminPanel /></PrivateRoute>}
          />

          {/* Ruta por defecto */}
          <Route path="/" element={<Navigate to="/dashboard" replace />} />
          <Route path="*" element={<Navigate to="/dashboard" replace />} />
        </Routes>

        <ToastContainer
          position="top-right"
          autoClose={5000}
          hideProgressBar={false}
          newestOnTop={false}
          closeOnClick
          rtl={false}
          pauseOnFocusLoss
          draggable
          pauseOnHover
        />
      </AuthProvider>
    </Router>
  )
}

export default App
