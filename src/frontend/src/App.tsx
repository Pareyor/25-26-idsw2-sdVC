import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/Login';
import Dashboard from './components/Dashboard';
import Layout from './components/Layout';
import DocenteList from './components/DocenteList';
import DocenteCreate from './components/DocenteCreate';
import DocenteEdit from './components/DocenteEdit';
import GradoList from './components/GradoList';
import GradoCreate from './components/GradoCreate';
import GradoEdit from './components/GradoEdit';
import AsignaturaList from './components/AsignaturaList';
import AsignaturaCreate from './components/AsignaturaCreate';
import AsignaturaEdit from './components/AsignaturaEdit';
import AlumnoList from './components/AlumnoList';
import AlumnoCreate from './components/AlumnoCreate';
import AlumnoEdit from './components/AlumnoEdit';
import PreguntaList from './components/PreguntaList';
import PreguntaCreate from './components/PreguntaCreate';
import PreguntaEdit from './components/PreguntaEdit';
import RespuestaEdit from './components/RespuestaEdit';
import GenerarExamenes from './components/GenerarExamenes';
import ConfirmarAsignacion from './components/ConfirmarAsignacion';
import CorregirExamenesList from './components/CorregirExamenesList';
import DetalleExamen from './components/DetalleExamen';
import { getCurrentUser } from './services/auth.service';

const PrivateRoute = ({ children }: { children: JSX.Element }) => {
  const user = getCurrentUser();
  return user ? children : <Navigate to="/login" />;
};

const RoleRoute = ({ children, allowedRoles }: { children: JSX.Element, allowedRoles: string[] }) => {
  const user = getCurrentUser();
  if (!user) return <Navigate to="/login" />;
  
  // Corregido: comparar string 'user.role' con el array 'allowedRoles'
  const hasAccess = user.role && allowedRoles.includes(user.role);
  
  return hasAccess ? children : <Navigate to="/dashboard" />;
};

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route 
          path="/dashboard" 
          element={
            <PrivateRoute>
              <Layout><Dashboard /></Layout>
            </PrivateRoute>
          } 
        />
        
        {/* Rutas ADMIN */}
        <Route 
          path="/docentes" 
          element={
            <RoleRoute allowedRoles={['ROLE_ADMIN']}>
              <Layout><DocenteList /></Layout>
            </RoleRoute>
          } 
        />
        <Route 
          path="/docentes/nuevo" 
          element={
            <RoleRoute allowedRoles={['ROLE_ADMIN']}>
              <Layout><DocenteCreate /></Layout>
            </RoleRoute>
          } 
        />
        <Route 
          path="/docentes/editar/:id" 
          element={
            <RoleRoute allowedRoles={['ROLE_ADMIN']}>
              <Layout><DocenteEdit /></Layout>
            </RoleRoute>
          } 
        />

        {/* Rutas DOCENTE */}
        <Route 
          path="/grados" 
          element={
            <RoleRoute allowedRoles={['ROLE_DOCENTE']}>
              <Layout><GradoList /></Layout>
            </RoleRoute>
          } 
        />
        <Route 
          path="/grados/nuevo" 
          element={
            <RoleRoute allowedRoles={['ROLE_DOCENTE']}>
              <Layout><GradoCreate /></Layout>
            </RoleRoute>
          } 
        />
        <Route 
          path="/grados/editar/:id" 
          element={
            <RoleRoute allowedRoles={['ROLE_DOCENTE']}>
              <Layout><GradoEdit /></Layout>
            </RoleRoute>
          } 
        />
        <Route 
          path="/asignaturas" 
          element={
            <RoleRoute allowedRoles={['ROLE_DOCENTE']}>
              <Layout><AsignaturaList /></Layout>
            </RoleRoute>
          } 
        />
        <Route 
          path="/asignaturas/nuevo" 
          element={
            <RoleRoute allowedRoles={['ROLE_DOCENTE']}>
              <Layout><AsignaturaCreate /></Layout>
            </RoleRoute>
          } 
        />
        <Route 
          path="/asignaturas/editar/:id" 
          element={
            <RoleRoute allowedRoles={['ROLE_DOCENTE']}>
              <Layout><AsignaturaEdit /></Layout>
            </RoleRoute>
          } 
        />
        <Route 
          path="/alumnos" 
          element={
            <RoleRoute allowedRoles={['ROLE_DOCENTE']}>
              <Layout><AlumnoList /></Layout>
            </RoleRoute>
          } 
        />
        <Route 
          path="/alumnos/nuevo" 
          element={
            <RoleRoute allowedRoles={['ROLE_DOCENTE']}>
              <Layout><AlumnoCreate /></Layout>
            </RoleRoute>
          } 
        />
        <Route 
          path="/alumnos/editar/:id" 
          element={
            <RoleRoute allowedRoles={['ROLE_DOCENTE']}>
              <Layout><AlumnoEdit /></Layout>
            </RoleRoute>
          } 
        />
        <Route 
          path="/preguntas" 
          element={
            <RoleRoute allowedRoles={['ROLE_DOCENTE']}>
              <Layout><PreguntaList /></Layout>
            </RoleRoute>
          } 
        />
        <Route 
          path="/preguntas/nuevo" 
          element={
            <RoleRoute allowedRoles={['ROLE_DOCENTE']}>
              <Layout><PreguntaCreate /></Layout>
            </RoleRoute>
          } 
        />
        <Route 
          path="/preguntas/editar/:id" 
          element={
            <RoleRoute allowedRoles={['ROLE_DOCENTE']}>
              <Layout><PreguntaEdit /></Layout>
            </RoleRoute>
          } 
        />
        <Route 
          path="/respuestas/editar/:id/:preguntaId" 
          element={
            <RoleRoute allowedRoles={['ROLE_DOCENTE']}>
              <Layout><RespuestaEdit /></Layout>
            </RoleRoute>
          } 
        />
        <Route 
          path="/examenes/generar" 
          element={
            <RoleRoute allowedRoles={['ROLE_DOCENTE']}>
              <Layout><GenerarExamenes /></Layout>
            </RoleRoute>
          } 
        />
        <Route 
          path="/examenes/confirmar" 
          element={
            <RoleRoute allowedRoles={['ROLE_DOCENTE']}>
              <Layout><ConfirmarAsignacion /></Layout>
            </RoleRoute>
          } 
        />
        <Route 
          path="/examenes/corregir" 
          element={
            <RoleRoute allowedRoles={['ROLE_DOCENTE']}>
              <Layout><CorregirExamenesList /></Layout>
            </RoleRoute>
          } 
        />
        <Route 
          path="/examenes/detalle/:id" 
          element={
            <RoleRoute allowedRoles={['ROLE_DOCENTE']}>
              <Layout><DetalleExamen /></Layout>
            </RoleRoute>
          } 
        />
        <Route path="/" element={<Navigate to="/dashboard" />} />
      </Routes>
    </Router>
  );
}

export default App;
