import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { getDocente, updateDocente } from '../services/docente.service';
import type { Docente } from '../services/docente.service';
import { ArrowLeft, Save, User } from 'lucide-react';
import './Formularios.css';

const DocenteEdit: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [docente, setDocente] = useState<(Docente & { password?: string }) | null>(null);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    if (id) {
      fetchDocente(parseInt(id));
    }
  }, [id]);

  const fetchDocente = async (docenteId: number) => {
    try {
      const response = await getDocente(docenteId);
      setDocente({ ...response.data, password: '' });
      setLoading(false);
    } catch (err: any) {
      setError('Error al cargar los datos del docente.');
      setLoading(false);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (!docente) return;
    const { name, value } = e.target;
    setDocente(prev => prev ? ({ ...prev, [name]: value }) : null);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!docente || !id) return;
    
    setSaving(true);
    setError('');

    try {
      await updateDocente(parseInt(id), docente);
      navigate('/docentes');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Error al actualizar el docente.');
    } finally {
      setSaving(false);
    }
  };

  if (loading) return <div className="loading-state">Cargando datos del docente...</div>;

  return (
    <div className="form-container">
      <div className="form-header-actions">
        <button 
          onClick={() => navigate('/docentes')}
          className="btn-icon"
          title="Volver"
        >
          <ArrowLeft size={24} />
        </button>
      </div>
      
      <h2>Editar Docente</h2>

      {docente && (
        <form onSubmit={handleSubmit} className="standard-form">
          {error && (
            <div className="error-message">
              {error}
            </div>
          )}

          <div className="form-group">
            <label>DNI / Usuario</label>
            <input
              type="text"
              name="username"
              required
              value={docente.username}
              onChange={handleChange}
            />
          </div>

          <div className="form-group">
            <label>Nueva Contraseña (dejar en blanco para no cambiar)</label>
            <input
              type="password"
              name="password"
              value={docente.password || ''}
              onChange={handleChange}
              placeholder="********"
            />
          </div>

          <div className="form-group">
            <label>Email</label>
            <input
              type="email"
              name="email"
              required
              value={docente.email}
              onChange={handleChange}
            />
          </div>

          <div className="form-group">
            <label>Nombre</label>
            <input
              type="text"
              name="nombre"
              required
              value={docente.nombre}
              onChange={handleChange}
            />
          </div>

          <div className="form-group">
            <label>Apellidos</label>
            <input
              type="text"
              name="apellidos"
              required
              value={docente.apellidos}
              onChange={handleChange}
            />
          </div>

          <div className="form-actions">
            <button
              type="submit"
              disabled={saving}
              className="btn btn-primary"
            >
              <Save size={20} />
              <span>{saving ? 'Guardando...' : 'Actualizar Docente'}</span>
            </button>
          </div>
        </form>
      )}
    </div>
  );
};

export default DocenteEdit;
