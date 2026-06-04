import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/Login';
import Dashboard from './components/Dashboard';
import DocenteList from './components/DocenteList';
import DocenteCreate from './components/DocenteCreate';
import DocenteEdit from './components/DocenteEdit';
import GradoList from './components/GradoList';
import GradoCreate from './components/GradoCreate';
import AsignaturaList from './components/AsignaturaList';
import AlumnoList from './components/AlumnoList';
import PreguntaList from './components/PreguntaList';
import { getCurrentUser } from './services/auth.service';

const PrivateRoute = ({ children }: { children: JSX.Element }) => {
  const user = getCurrentUser();
  return user ? children : <Navigate to="/login" />;
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
              <Dashboard />
            </PrivateRoute>
          } 
        />
        <Route 
          path="/docentes" 
          element={
            <PrivateRoute>
              <DocenteList />
            </PrivateRoute>
          } 
        />
        <Route 
          path="/docentes/nuevo" 
          element={
            <PrivateRoute>
              <DocenteCreate />
            </PrivateRoute>
          } 
        />
        <Route 
          path="/docentes/editar/:id" 
          element={
            <PrivateRoute>
              <DocenteEdit />
            </PrivateRoute>
          } 
        />
        <Route 
          path="/grados" 
          element={
            <PrivateRoute>
              <GradoList />
            </PrivateRoute>
          } 
        />
        <Route 
          path="/grados/nuevo" 
          element={
            <PrivateRoute>
              <GradoCreate />
            </PrivateRoute>
          } 
        />
        <Route 
          path="/asignaturas" 
          element={
            <PrivateRoute>
              <AsignaturaList />
            </PrivateRoute>
          } 
        />
        <Route 
          path="/alumnos" 
          element={
            <PrivateRoute>
              <AlumnoList />
            </PrivateRoute>
          } 
        />
        <Route 
          path="/preguntas" 
          element={
            <PrivateRoute>
              <PreguntaList />
            </PrivateRoute>
          } 
        />
        <Route path="/" element={<Navigate to="/dashboard" />} />
      </Routes>
    </Router>
  );
}

export default App;
