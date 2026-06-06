import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { getAlumno, updateAlumno } from '../services/alumno.service';
import { getGrados } from '../services/grado.service';
import type { Alumno } from '../services/alumno.service';
import { ArrowLeft, Save, User } from 'lucide-react';
import './Formularios.css';

const AlumnoEdit: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [alumno, setAlumno] = useState<any>(null);
  const [grados, setGrados] = useState<any[]>([]);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    if (id) {
      Promise.all([
        getAlumno(parseInt(id)),
        getGrados()
      ]).then(([alumnoRes, gradosRes]) => {
        setAlumno(alumnoRes.data);
        setGrados(gradosRes.data);
        setLoading(false);
      }).catch(() => {
        setError('Error al cargar los datos.');
        setLoading(false);
      });
    }
  }, [id]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    if (!alumno) return;
    const { name, value } = e.target;
    setAlumno(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!alumno || !id) return;
    
    setSaving(true);
    setError('');

    try {
      await updateAlumno(parseInt(id), { ...alumno, gradoId: parseInt(alumno.gradoId) });
      navigate('/alumnos');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Error al actualizar.');
    } finally {
      setSaving(false);
    }
  };

  if (loading) return <div className="loading-state">Cargando...</div>;

  return (
    <div className="form-container">
      <div className="form-header-actions">
        <button onClick={() => navigate('/alumnos')} className="btn-icon">
          <ArrowLeft size={24} />
        </button>
      </div>
      
      <h2>Editar Alumno</h2>

      {alumno && (
        <form onSubmit={handleSubmit} className="standard-form">
          {error && <div className="error-message">{error}</div>}

          <div className="form-group">
            <label>DNI</label>
            <input type="text" name="dni" required value={alumno.dni} onChange={handleChange} />
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
            <input type="text" name="curso" required value={alumno.curso || ''} onChange={handleChange} />
          </div>

          <div className="form-group">
            <label>Grado</label>
            <select name="gradoId" required value={alumno.gradoId} onChange={handleChange}>
              <option value="">Seleccione un grado</option>
              {grados.map(g => <option key={g.id} value={g.id}>{g.titulo}</option>)}
            </select>
          </div>

          <div className="form-actions">
            <button type="submit" disabled={saving} className="btn btn-primary">
              <Save size={20} />
              <span>{saving ? 'Guardando...' : 'Actualizar Alumno'}</span>
            </button>
          </div>
        </form>
      )}
    </div>
  );
};

export default AlumnoEdit;
