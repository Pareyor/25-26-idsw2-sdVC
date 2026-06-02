import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/Login';
import Dashboard from './components/Dashboard';
import DocenteList from './components/DocenteList';
import GradoList from './components/GradoList';
import AsignaturaList from './components/AsignaturaList';
import AlumnoList from './components/AlumnoList';
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
          path="/grados" 
          element={
            <PrivateRoute>
              <GradoList />
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
        <Route path="/" element={<Navigate to="/dashboard" />} />
      </Routes>
    </Router>
  );
}

export default App;
