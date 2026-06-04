import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { getGrado, updateGrado } from '../services/grado.service';
import type { Grado } from '../services/grado.service';
import { ArrowLeft, Save, GraduationCap } from 'lucide-react';

const GradoEdit: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [grado, setGrado] = useState<Grado | null>(null);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    if (id) {
      fetchGrado(parseInt(id));
    }
  }, [id]);

  const fetchGrado = async (gradoId: number) => {
    try {
      const response = await getGrado(gradoId);
      setGrado(response.data);
      setLoading(false);
    } catch (err: any) {
      setError('Error al cargar los datos del grado.');
      setLoading(false);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (!grado) return;
    const { name, value } = e.target;
    setGrado(prev => prev ? ({ ...prev, [name]: value }) : null);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!grado || !id) return;
    
    setSaving(true);
    setError('');

    try {
      await updateGrado(parseInt(id), grado);
      navigate('/grados');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Error al actualizar el grado.');
    } finally {
      setSaving(false);
    }
  };

  if (loading) return <div className="p-8 text-center">Cargando datos del grado...</div>;

  return (
    <div className="min-h-screen bg-gray-50 p-4 md:p-8">
      <div className="max-w-2xl mx-auto">
        <div className="flex items-center gap-4 mb-8">
          <button 
            onClick={() => navigate('/grados')}
            className="p-2 hover:bg-gray-200 rounded-full transition-colors"
          >
            <ArrowLeft size={24} />
          </button>
          <h1 className="text-3xl font-bold text-gray-800">Editar Grado</h1>
        </div>

        {grado && (
          <div className="bg-white rounded-xl shadow-md border border-gray-200 overflow-hidden">
            <div className="p-6 bg-blue-600 text-white flex items-center gap-3">
              <GraduationCap size={24} />
              <h2 className="text-xl font-semibold">Datos del Grado: {grado.codigo}</h2>
            </div>

            <form onSubmit={handleSubmit} className="p-6 space-y-6">
              {error && (
                <div className="bg-red-50 text-red-600 p-4 rounded-lg border border-red-100 text-sm">
                  {error}
                </div>
              )}

              <div className="space-y-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Código del Grado</label>
                  <input
                    type="text"
                    name="codigo"
                    required
                    className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none"
                    value={grado.codigo}
                    onChange={handleChange}
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Título del Grado</label>
                  <input
                    type="text"
                    name="titulo"
                    required
                    className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none"
                    value={grado.titulo}
                    onChange={handleChange}
                  />
                </div>
              </div>

              <div className="flex justify-end pt-4">
                <button
                  type="submit"
                  disabled={saving}
                  className="flex items-center gap-2 bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors shadow-md disabled:bg-blue-400"
                >
                  <Save size={20} />
                  <span>{saving ? 'Guardando...' : 'Actualizar Grado'}</span>
                </button>
              </div>
            </form>
          </div>
        )}
      </div>
    </div>
  );
};

export default GradoEdit;
