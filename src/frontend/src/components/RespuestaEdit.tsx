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
    <div className="min-h-screen bg-gray-50 p-4 md:p-8">
      <div className="max-w-xl mx-auto">
        <div className="flex items-center gap-4 mb-8">
          <button 
            onClick={() => navigate(`/preguntas/editar/${preguntaId}`)}
            className="p-2 hover:bg-gray-200 rounded-full transition-colors"
          >
            <ArrowLeft size={24} />
          </button>
          <h1 className="text-3xl font-bold text-gray-800">Editar Opción</h1>
        </div>

        <form onSubmit={handleSubmit} className="bg-white rounded-xl shadow-md border border-gray-200 overflow-hidden">
          <div className="p-6 bg-blue-600 text-white flex items-center gap-3">
            <MessageSquare size={24} />
            <h2 className="text-xl font-semibold">Modificar Contenido</h2>
          </div>

          <div className="p-6 space-y-6">
            {error && <div className="bg-red-50 text-red-600 p-4 rounded-lg text-sm">{error}</div>}

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">Contenido de la respuesta</label>
              <textarea
                required
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                value={respuesta.opcion}
                onChange={(e) => setRespuesta({...respuesta, opcion: e.target.value})}
                rows={3}
              />
            </div>

            <div className="flex items-center gap-3 p-4 bg-gray-50 rounded-lg border border-gray-100">
              <input 
                type="checkbox" 
                id="esCorrecta"
                className="w-5 h-5 text-blue-600 rounded focus:ring-blue-500"
                checked={respuesta.esCorrecta} 
                onChange={(e) => setRespuesta({...respuesta, esCorrecta: e.target.checked})} 
              />
              <label htmlFor="esCorrecta" className="font-medium text-gray-700 cursor-pointer">
                Esta opción es correcta
              </label>
            </div>

            <div className="flex justify-end pt-4 border-t">
              <button type="submit" disabled={saving} className="flex items-center gap-2 bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors">
                <Save size={20} />
                <span>{saving ? 'Guardando...' : 'Guardar Cambios'}</span>
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
};

export default RespuestaEdit;
