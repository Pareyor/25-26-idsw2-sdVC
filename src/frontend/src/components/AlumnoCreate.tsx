import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { createAlumno } from '../services/alumno.service';
import { getGrados } from '../services/grado.service';
import { ArrowLeft, Save, User } from 'lucide-react';
import './Formularios.css';

const AlumnoCreate: React.FC = () => {
  const [alumno, setAlumno] = useState({
    dni: '',
    nombre: '',
    apellidos: '',
    gradoId: '',
    curso: '25/26'
  });
  const [grados, setGrados] = useState<any[]>([]);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    getGrados().then(res => setGrados(res.data));
  }, []);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setAlumno(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      await createAlumno({ ...alumno, gradoId: parseInt(alumno.gradoId) });
      navigate('/alumnos');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Error al crear el alumno.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="form-container">
      <div className="form-header" style={{ display: 'flex', alignItems: 'center', gap: '1rem', marginBottom: '2rem' }}>
        <button onClick={() => navigate('/alumnos')} className="btn-icon">
          <ArrowLeft size={24} />
        </button>
        <h1>Añadir Nuevo Alumno</h1>
      </div>

      <form onSubmit={handleSubmit} className="standard-form">
        <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem', marginBottom: '1rem' }}>
          <User size={24} color="var(--primary)" />
          <h2 style={{ margin: 0, textAlign: 'left', fontSize: '1.5rem' }}>Datos del Alumno</h2>
        </div>

        {error && <div className="error-message">{error}</div>}

        <div className="form-group">
          <label>DNI</label>
          <input type="text" name="dni" required value={alumno.dni} onChange={handleChange} placeholder="Ej: 12345678A" />
        </div>

        <div className="form-group">
          <label>Nombre</label>
          <input type="text" name="nombre" required value={alumno.nombre} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>Apellidos</label>
          <input type="text" name="apellidos" required value={alumno.apellidos} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>Curso</label>
          <input type="text" name="curso" required value={alumno.curso} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>Grado</label>
          <select name="gradoId" required value={alumno.gradoId} onChange={handleChange}>
            <option value="">Seleccione un grado</option>
            {grados.map(g => <option key={g.id} value={g.id}>{g.titulo}</option>)}
          </select>
        </div>

        <div className="form-actions">
          <button type="submit" disabled={loading} className="btn btn-primary">
            <Save size={20} />
            <span>{loading ? 'Guardando...' : 'Guardar Alumno'}</span>
          </button>
        </div>
      </form>
    </div>
  );
};

export default AlumnoCreate;
