import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { getAlumno, updateAlumno } from '../services/alumno.service';
import { getGrados } from '../services/grado.service';
import type { Grado } from '../services/grado.service';
import { ArrowLeft, Save, User } from 'lucide-react';
import './Formularios.css';

const AlumnoEdit: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [alumno, setAlumno] = useState({
    dni: '',
    nombre: '',
    apellidos: '',
    gradoId: 0,
  });
  const [grados, setGrados] = useState<Grado[]>([]);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    if (id) {
      loadData(parseInt(id));
    }
  }, [id]);

  const loadData = async (alumnoId: number) => {
    try {
      const [alumnoRes, gradosRes] = await Promise.all([
        getAlumno(alumnoId),
        getGrados()
      ]);
      setAlumno(alumnoRes.data);
      setGrados(gradosRes.data);
      setLoading(false);
    } catch (err: any) {
      setError('Error al cargar los datos.');
      setLoading(false);
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

    setSaving(true);
    setError('');

    try {
      await updateAlumno(parseInt(id!), alumno as any);
      navigate('/alumnos');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Error al actualizar el alumno.');
    } finally {
      setSaving(false);
    }
  };

  if (loading) return <div style={{ padding: '2rem', textAlign: 'center', color: 'var(--text-main)' }}>Cargando datos del alumno...</div>;

  return (
    <div className="form-container">
      <div className="form-header" style={{ display: 'flex', alignItems: 'center', gap: '1rem', marginBottom: '2rem' }}>
        <button 
          onClick={() => navigate('/alumnos')}
          className="btn-icon"
        >
          <ArrowLeft size={24} />
        </button>
        <h1>Editar Alumno</h1>
      </div>

      <form onSubmit={handleSubmit} className="standard-form">
        <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem', marginBottom: '1rem' }}>
          <User size={24} color="var(--primary)" />
          <h2 style={{ margin: 0, textAlign: 'left', fontSize: '1.5rem' }}>Datos de: {alumno.nombre} {alumno.apellidos}</h2>
        </div>

        {error && (
          <div className="error-message" style={{ backgroundColor: 'rgba(244, 63, 94, 0.1)', color: 'var(--accent)', padding: '1rem', borderRadius: '8px', border: '1px solid var(--accent)', marginBottom: '1rem' }}>
            {error}
          </div>
        )}

        <div className="form-group">
          <label>DNI / NIE</label>
          <input
            type="text"
            name="dni"
            required
            pattern="^([XYZxyz]\d{7}[A-Za-z]|\d{8}[A-Za-z])$"
            title="Formato inválido: 8 dígitos y 1 letra (DNI) o X,Y,Z seguido de 7 dígitos y 1 letra (NIE)"
            value={alumno.dni}
            onChange={handleChange}
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
            value={alumno.gradoId || 0}
            onChange={handleChange}
          >
            <option value={0}>Seleccione un grado...</option>
            {grados.map(grado => (
              <option key={grado.id} value={grado.id}>
                [{grado.codigo}] {grado.titulo}
              </option>
            ))}
          </select>
        </div>

        <div className="form-actions">
          <button
            type="submit"
            disabled={saving}
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
            <span>{saving ? 'Guardando...' : 'Actualizar Alumno'}</span>
          </button>
        </div>
      </form>
    </div>
  );
};

export default AlumnoEdit;
