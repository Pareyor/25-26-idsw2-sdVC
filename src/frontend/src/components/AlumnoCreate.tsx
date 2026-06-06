import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createAlumno } from '../services/alumno.service';
import { getGrados } from '../services/grado.service';
import type { Grado } from '../services/grado.service';
import { ArrowLeft, Save, User } from 'lucide-react';
import './Formularios.css';

const AlumnoCreate: React.FC = () => {
  const [alumno, setAlumno] = useState({
    dni: '',
    nombre: '',
    apellidos: '',
    gradoId: 0,
  });
  const [grados, setGrados] = useState<Grado[]>([]);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const [loadingGrados, setLoadingGrados] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    fetchGrados();
  }, []);

  const fetchGrados = async () => {
    try {
      const response = await getGrados();
      setGrados(response.data);
      setLoadingGrados(false);
    } catch (err) {
      setError('Error al cargar la lista de grados.');
      setLoadingGrados(false);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setAlumno(prev => ({ 
      ...prev, 
      [name]: name === 'gradoId' ? parseInt(value) : value 
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (alumno.gradoId === 0) {
      setError('Debe seleccionar un grado.');
      return;
    }

    setLoading(true);
    setError('');

    try {
      await createAlumno(alumno);
      navigate('/alumnos');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Error al crear el alumno. Verifique el DNI.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="form-container">
      <div className="form-header" style={{ display: 'flex', alignItems: 'center', gap: '1rem', marginBottom: '2rem' }}>
        <button 
          onClick={() => navigate('/alumnos')}
          className="btn-icon"
        >
          <ArrowLeft size={24} />
        </button>
        <h1>Añadir Nuevo Alumno</h1>
      </div>

      <form onSubmit={handleSubmit} className="standard-form">
        <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem', marginBottom: '1rem' }}>
          <User size={24} color="var(--primary)" />
          <h2 style={{ margin: 0, textAlign: 'left', fontSize: '1.5rem' }}>Datos del Alumno</h2>
        </div>

        {error && (
          <div className="error-message" style={{ backgroundColor: 'rgba(244, 63, 94, 0.1)', color: 'var(--accent)', padding: '1rem', borderRadius: '8px', border: '1px solid var(--accent)', marginBottom: '1rem' }}>
            {error}
          </div>
        )}

        <div className="form-group">
          <label>DNI</label>
          <input
            type="text"
            name="dni"
            required
            pattern="^([XYZxyz]\d{7}[A-Za-z]|\d{8}[A-Za-z])$"
            title="Formato inválido: 8 dígitos y 1 letra (DNI) o X,Y,Z seguido de 7 dígitos y 1 letra (NIE)"
            value={alumno.dni}
            onChange={handleChange}
            placeholder="Ej: 12345678X o X1234567A"
          />
        </div>

        <div className="form-group">
          <label>Nombre</label>
          <input
            type="text"
            name="nombre"
            required
            value={alumno.nombre}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label>Apellidos</label>
          <input
            type="text"
            name="apellidos"
            required
            value={alumno.apellidos}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label>Grado</label>
          <select
            name="gradoId"
            required
            value={alumno.gradoId}
            onChange={handleChange}
            disabled={loadingGrados}
          >
            <option value={0}>Seleccione un grado...</option>
            {grados.map(grado => (
              <option key={grado.id} value={grado.id}>
                [{grado.codigo}] {grado.titulo}
              </option>
            ))}
          </select>
          {loadingGrados && <p style={{ fontSize: '0.75rem', color: 'var(--text-muted)', marginTop: '0.25rem' }}>Cargando grados...</p>}
        </div>

        <div className="form-actions">
          <button
            type="submit"
            disabled={loading || loadingGrados}
            className="btn btn-primary"
            style={{ 
              display: 'flex', 
              alignItems: 'center', 
              gap: '0.5rem', 
              padding: '0.75rem 1.5rem', 
              borderRadius: '12px', 
              backgroundColor: 'var(--primary)', 
              color: 'white', 
              border: 'none', 
              cursor: 'pointer' 
            }}
          >
            <Save size={20} />
            <span>{loading ? 'Guardando...' : 'Guardar Alumno'}</span>
          </button>
        </div>
      </form>
    </div>
  );
};

export default AlumnoCreate;
