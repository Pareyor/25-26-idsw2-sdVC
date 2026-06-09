import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { updateRespuesta } from '../services/respuesta.service';
import { getPregunta } from '../services/pregunta.service';
import type { Respuesta } from '../types/pregunta';
import { ArrowLeft, Save, MessageSquare } from 'lucide-react';

const RespuestaEdit: React.FC = () => {
  const { id, preguntaId } = useParams<{ id: string, preguntaId: string }>();
  const [respuesta, setRespuesta] = useState<Respuesta>({
    opcion: '',
    esCorrecta: false,
  });
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    if (id && preguntaId) {
      loadData();
    }
  }, [id, preguntaId]);

  const loadData = async () => {
    try {
      const response = await getPregunta(parseInt(preguntaId!));
      const resp = response.data.respuestas.find(r => r.id === parseInt(id!));
      if (resp) {
        setRespuesta(resp);
      } else {
        setError('Respuesta no encontrada');
      }
      setLoading(false);
    } catch (err) {
      setError('Error al cargar los datos');
      setLoading(false);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setSaving(true);
    setError('');

    try {
      await updateRespuesta(parseInt(id!), respuesta);
      navigate(`/preguntas/editar/${preguntaId}`);
    } catch (err: any) {
      setError('Error al actualizar la respuesta');
    } finally {
      setSaving(false);
    }
  };

  if (loading) return <div className="p-8 text-center">Cargando respuesta...</div>;

  return (
    <div className="list-container">
      <div className="form-header">
        <button 
            onClick={() => navigate(`/preguntas/editar/${preguntaId}`)}
            className="btn-icon"
        >
            <ArrowLeft size={24} />
        </button>
        <h2>Editar Opción de Respuesta</h2>
      </div>

      <form onSubmit={handleSubmit} className="standard-form">
        <div className="form-card">
          <div className="form-group">
            <label>Contenido de la respuesta</label>
            <textarea
              required
              className="form-control"
              value={respuesta.opcion}
              onChange={(e) => setRespuesta({...respuesta, opcion: e.target.value})}
              rows={3}
              placeholder="Escribe aquí el contenido de la respuesta..."
            />
          </div>

          <div className="form-group-checkbox">
            <input 
              type="checkbox" 
              id="esCorrecta"
              className="form-checkbox"
              checked={respuesta.esCorrecta} 
              onChange={(e) => setRespuesta({...respuesta, esCorrecta: e.target.checked})} 
            />
            <label htmlFor="esCorrecta">
              Marcar como respuesta correcta
            </label>
          </div>
        </div>

        {error && <div className="error-message">{error}</div>}

        <div className="form-actions">
          <button type="submit" disabled={saving} className="btn btn-primary">
            <Save size={20} />
            <span>{saving ? 'Guardando...' : 'Guardar Cambios'}</span>
          </button>
        </div>
      </form>
    </div>
  );
};

export default RespuestaEdit;
