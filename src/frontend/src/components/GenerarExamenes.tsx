import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import * as asignaturaService from '../services/asignatura.service';
import * as gradoService from '../services/grado.service';
import examenService from '../services/examen.service';
import './GenerarExamenes.css';

const GenerarExamenes: React.FC = () => {
  const navigate = useNavigate();
  const [asignaturas, setAsignaturas] = useState<any[]>([]);
  const [grados, setGrados] = useState<any[]>([]);
  
  const [config, setConfig] = useState<any>({
    asignaturaId: '',
    tipoExamen: 'PARCIAL_1',
    temas: ['TEORIA'],
    numPreguntas: 10,
    configuracionesGrado: []
  });

  useEffect(() => {
    asignaturaService.getAsignaturas().then(res => setAsignaturas(res.data));
    gradoService.getGrados().then(res => setGrados(res.data));
  }, []);

  const handleAsignaturaChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const asignaturaId = e.target.value;
    setConfig({ ...config, asignaturaId, configuracionesGrado: [] });
  };

  const addGradoConfig = (gradoId: string) => {
    if (!gradoId) return;
    
    if (config.configuracionesGrado.find((c: any) => c.gradoId === parseInt(gradoId))) {
      alert("Este grado ya ha sido añadido.");
      return;
    }

    const newConfig = {
      gradoId: parseInt(gradoId),
      numExamenes: '',
      numPreguntas: '',
      proporcionFacil: '',
      proporcionMedia: '',
      proporcionDificil: ''
    };
    setConfig({ ...config, configuracionesGrado: [...config.configuracionesGrado, newConfig] });
  };

  const updateGradoConfig = (index: number, field: string, value: string) => {
    const newConfigs = [...config.configuracionesGrado];
    newConfigs[index][field] = value === '' ? '' : parseInt(value);
    setConfig({ ...config, configuracionesGrado: newConfigs });
  };

  const removeGradoConfig = (index: number) => {
    const newConfigs = config.configuracionesGrado.filter((_: any, i: number) => i !== index);
    setConfig({ ...config, configuracionesGrado: newConfigs });
  };

  const handleCancel = async () => {
    try {
      await examenService.cancelarGeneracion();
    } catch (error) {
      console.error("Error al cancelar la generación:", error);
    } finally {
      navigate('/dashboard');
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await examenService.generarExamenes(config);
      navigate('/examenes/confirmar');
    } catch (error) {
      console.error(error);
      alert('Error al generar exámenes: ' + (error as any).response?.data?.message || 'Error desconocido');
    }
  };

  return (
    <div className="examen-container">
      <h1>Generar Exámenes</h1>
      <form onSubmit={handleSubmit} className="examen-form">
        <div className="form-group">
          <label>Asignatura:</label>
          <select onChange={handleAsignaturaChange} value={config.asignaturaId}>
            <option value="">Seleccione una asignatura</option>
            {asignaturas.map(a => <option key={a.id} value={a.id}>{a.titulo}</option>)}
          </select>
        </div>

        <div className="form-group">
          <label>Tipo de Examen:</label>
          <select value={config.tipoExamen} onChange={(e) => setConfig({...config, tipoExamen: e.target.value})}>
            <option value="PARCIAL_1">Parcial 1</option>
            <option value="PARCIAL_2">Parcial 2</option>
            <option value="PARCIAL_3">Parcial 3</option>
            <option value="FINAL">Final</option>
            <option value="EXTRAORDINARIO">Extraordinario</option>
          </select>
        </div>

        {config.asignaturaId && (
          <div className="form-group">
            <label>Añadir Grado a la configuración:</label>
            <select onChange={(e) => addGradoConfig(e.target.value)} value="">
              <option value="">Seleccione un grado</option>
              {grados.map(g => <option key={g.id} value={g.id}>{g.titulo}</option>)}
            </select>
          </div>
        )}

        {config.configuracionesGrado.map((cfg: any, index: number) => {
          const grado = grados.find(g => g.id === cfg.gradoId);
          return (
            <div key={index} className="grado-config">
              <div>
                <h3>Grado: {grado ? grado.titulo : cfg.gradoId}</h3>
                <div className="form-group" style={{display: 'flex', gap: '10px'}}>
                  <input type="number" placeholder="Núm Examenes" value={cfg.numExamenes} onChange={(e) => updateGradoConfig(index, 'numExamenes', e.target.value)} />
                  <input type="number" placeholder="Núm Preguntas" value={cfg.numPreguntas} onChange={(e) => updateGradoConfig(index, 'numPreguntas', e.target.value)} />
                  <input type="number" placeholder="% Fácil" value={cfg.proporcionFacil} onChange={(e) => updateGradoConfig(index, 'proporcionFacil', e.target.value)} />
                  <input type="number" placeholder="% Media" value={cfg.proporcionMedia} onChange={(e) => updateGradoConfig(index, 'proporcionMedia', e.target.value)} />
                  <input type="number" placeholder="% Difícil" value={cfg.proporcionDificil} onChange={(e) => updateGradoConfig(index, 'proporcionDificil', e.target.value)} />
                </div>
              </div>
              <button type="button" onClick={() => removeGradoConfig(index)} className="btn btn-danger">Eliminar</button>
            </div>
          );
        })}

        <div className="form-actions">
          <button type="submit" className="btn btn-primary">Generar Exámenes</button>
          <button type="button" onClick={handleCancel} className="btn" style={{marginLeft: '10px'}}>Cancelar</button>
        </div>
      </form>
    </div>
  );
};

export default GenerarExamenes;
